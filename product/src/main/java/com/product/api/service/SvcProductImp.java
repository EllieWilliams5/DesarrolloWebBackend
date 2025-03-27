package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.common.dto.ApiResponse;
import com.product.common.mapper.MapperProduct;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

/**
 * +++++ Punto 1 de la práctica 6 +++++
 * 
 * Servicio que implementa las operaciones relacionadas con productos.
 * Incluye operaciones CRUD para productos, además de la lógica
 * para recuperar imágenes asociadas en formato base64.
 */
@Service
public class SvcProductImp implements SvcProduct {
	
	//Repositorio de la entidad product
	@Autowired
	RepoProduct repo;
	
    // Repositorio para las imagenes asociadas a productos
	@Autowired
	RepoProductImage repoProductImage;
	
	// Chacaleando lo del maestro, use mapper que transforma dto en entidades y viceversa 
	@Autowired
	MapperProduct mapper;
	
    // Ruta base del sistema de archivos para almacenar imágenes
	@Value("${app.upload.dir}")
	private String uploadDir;

    /**
     * Consulta y devuelve la lista de todos los productos.
     * Utiliza el mapper para convertir entidades a DTOs de listado.
     */
	@Override
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		try {
			List<Product> products = repo.findAll();
			return new ResponseEntity<>(mapper.fromProductList(products), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}


    /**
     * Consulta el detalle de un producto, incluyendo su imagen codificada en Base64 (o sus imagenes).
     * Si existe al menos una imagen válida, se asigna como imagen destacada.
     */
	@Override
	public ResponseEntity<DtoProductOut> getProduct(Integer id) {
		try {
            validateProductId(id);
            
			DtoProductOut product = repo.getProduct(id);
			if (product == null) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
			}
			
			// Todas las imágenes asociadas al producto
			List<String> imagenes = readProductImages(id);
			
			// Si hay al menos una imagen, se define la primera como imagen destacada
			if (!imagenes.isEmpty()) {
				product.setImage(imagenes.get(0));
			}
			// Se asigna la lista completa de imágenes al DTO
			product.setImages(imagenes);
			
			return new ResponseEntity<>(product, HttpStatus.OK);
		} catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
	

    /**
     * Método auxuliar.
     * Lee los archivos físicos de imagen asociados a un producto y los convierte a Base64.
     */
	private List<String> readProductImages(Integer productId) {
	    try {
	        List<ProductImage> productImages = repoProductImage.findByProductId(productId);
	        List<String> imageUrls = new ArrayList<>();
	        
	        // Si no hay imágenes, se retorna una lista vacía
	        if(productImages == null || productImages.isEmpty()){
	            return imageUrls;
	        }
	        
	        for (ProductImage image : productImages) {
	            String imageUrl = image.getImage();

	            if (imageUrl.startsWith("/")) {
	                imageUrl = imageUrl.substring(1);
	            }
	            
	            // Se construye el path absoluto del archivo
	            Path imagePath = Paths.get(uploadDir, imageUrl);
	            
	            if (Files.exists(imagePath)) {
	                // Si el archivo existe, leerlo y codificarlo a Base 64
	                byte[] imageBytes = Files.readAllBytes(imagePath);
	                imageUrls.add(Base64.getEncoder().encodeToString(imageBytes));
	            } else {
	                // Si no existe, se agrega una cadena vacía
	                imageUrls.add("");
	            }
	        }
	        
	        return imageUrls;
	        
	    } catch (DataAccessException e) {
	        throw new DBAccessException(e);
	    } catch (IOException e) {
	        throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
	    }
	}


    /**
     * Registra un nuevo producto en la base de datos.
     */
	@Override
	public ResponseEntity<ApiResponse> createProduct(DtoProductIn in) {
		try {
			Product product = mapper.fromDto(in);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido registrado"), HttpStatus.CREATED);
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");

			throw new DBAccessException(e);
		}
	}

    /**
     * Actualiza un producto existente con nuevos datos.
     */
	@Override
	public ResponseEntity<ApiResponse> updateProduct(Integer id, DtoProductIn in) {
		try {
			validateProductId(id);
			Product product = mapper.fromDto(id, in);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido actualizado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_product_gtin"))
				throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
			if (e.getLocalizedMessage().contains("ux_product_product"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
			if (e.getLocalizedMessage().contains("fk_product_category"))
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");

			throw new DBAccessException(e);
		}
	}

    /**
     * Cambia el estado de un producto a 1, por lo que lo activa.
     */
	@Override
	public ResponseEntity<ApiResponse> enableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(1);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido activado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

    /**
     * Cambia el estado de un producto a 0, por lo que lo desactiva.
     */
	@Override
	public ResponseEntity<ApiResponse> disableProduct(Integer id) {
		try {
			validateProductId(id);
			Product product = repo.findById(id).get();
			product.setStatus(0);
			repo.save(product);
			return new ResponseEntity<>(new ApiResponse("El producto ha sido desactivado"), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}
	
    /**
     * Método auxiliar.
     * Valida si existe un producto con el ID especificado.
     */
	private void validateProductId(Integer id) {
		try {
			if(repo.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
			}
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}
}
