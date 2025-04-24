package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProductImage;
import com.product.common.dto.ApiResponse;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

/**
 * +++++ Punto 2 de la práctica 6 +++++
 *
 * Servicio para gestionar imágenes asociadas a productos.
 * Permite guardar una imagen en base64 y eliminarla tanto del sistema de archivos como de la base de datos.
 */
@Service
public class SvcProductImageImp implements SvcProductImage {
	
    // Repositorio de imágenes
	@Autowired
	RepoProductImage repo;
	

    // Ruta base para archivos
	@Value("${app.upload.dir}")
	private String uploadDir;

    /**
     * Guarda una imagen para un producto. 
     * La imagen se recibe en formato base64, se decodifica, se guarda
     * y se registra en la base de datos como ruta relativa.
     */
	@Override
	public ResponseEntity<ApiResponse> createProductImage(DtoProductImageIn in) {
		try {
			// Eliminar el prefijo "data:image/png;base64," si existe ---- ???
			if (in.getImage().startsWith("data:image")) {
			int commaIndex = in.getImage().indexOf(",");
				if (commaIndex != -1) {
					in.setImage(in.getImage().substring(commaIndex + 1));
				}
			}

			// Decodifica a bytes
			byte[] imageBytes = Base64.getDecoder().decode(in.getImage());

			// Genera un nombre único para la imagen con extensión png
			String fileName = UUID.randomUUID().toString() + ".png";

			// Construye la rutadonde se guardara la imagen
			Path imagePath = Paths.get(uploadDir, "img", "product", fileName);
		    
			// Asegurarse de que el directorio exista
			Files.createDirectories(imagePath.getParent());

			// Guarda el archivo
			Files.write(imagePath, imageBytes);
			
			// ProductImage productImage = repo.findByProductById(in.getProduct_id());
			// if(productImage == null) {

	        // Crear y guardar el registro en la base de datos
			ProductImage productImage = new ProductImage();
			productImage.setProduct_id(in.getProduct_id());
			// Este es el erroooor!!
			// Es la ruta relaitva.
			productImage.setImage("img/product/" + fileName); 
			productImage.setStatus(1); 

			repo.save(productImage);
			
			
			return new ResponseEntity<>(new ApiResponse("La imagen del producto ha sido actualizada"), HttpStatus.OK);
		}catch (DataAccessException e) {
		    throw new DBAccessException(e);
		}catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
		}

	}
	

    /**
     * Elimina una imagen de producto. Se borra tanto el archivo físico como el registro en la BD.
     */
	@Override
	public ResponseEntity<ApiResponse> deleteProductImage(Integer id) {
	    try {
	        // Buscar la imagen
	        ProductImage productImage = repo.findById(id)
	            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen no existe"));
	        
	        // Obtener la ruta y limpiarla
	        String imageUrl = productImage.getImage();
	        if (imageUrl.startsWith("/")) {
	            imageUrl = imageUrl.substring(1);
	        }
	        
	        // Construir la ruta absoluta usando el directorio configurado
	        Path imagePath = Paths.get(uploadDir, imageUrl);
	        
	        // Si el archivo existe en el sistema, eliminarlo.
	        if (Files.exists(imagePath)) {
	            Files.delete(imagePath);
	        }
	        
	        // Eliminar el registro de la base de datos
	        repo.delete(productImage);
	        
	        return new ResponseEntity<>(new ApiResponse("La imagen ha sido eliminada"), HttpStatus.OK);
	    } catch (DataAccessException e) {
	        throw new DBAccessException(e);
	    } catch (IOException e) {
	        throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el archivo");
	    }
	}


}
