package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.common.dto.ApiResponse;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

/**
 * Implementación del servicio para la gestión de categorías de productos.
 */
@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    RepoCategory repo;

    /**
     * Regresa todas las categorías disponibles en la base de datos.
     * @return ResponseEntity con la lista de categorías y el código de estado HTTP.
     */
    // Deberia cambiar el método?... Tengo sueño...
    @Override
    public ResponseEntity<List<Category>> getCategories() {
    	try {
    		return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
    	} catch (DataAccessException e) {
    		System.out.println(e.getLocalizedMessage());
    		throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos");
    	}
    }

    // Nuevos métodos. 
    // +++++ Punto 5 de la práctica 5. jeje +++++

    /**
     * Regresa únicamente las categorías activas (status = 1).
     * @return ResponseEntity con la lista de categorías activas y el código de estado HTTP.
     */
	@Override
	public ResponseEntity<List<Category>> getActiveCategories() {
		try {
			return new ResponseEntity<>(repo.getActiveCategories(), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

    /**
     * Regresa una categoría específica por su id.
     * @param id Identificador único de la categoría.
     * @return ResponseEntity con la categoría encontrada y el código de estado HTTP.
     * @throws ApiException Si el id de la categoría no existe.
     */
	@Override
	public ResponseEntity<Category> getCategory(int id) {
		try {
			validateCategoryId(id);
			return new ResponseEntity<>(repo.getCategory(id), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

    /**
     * Crea una nueva categoría en la base de datos.
     * @param in DTO que contiene los datos de la nueva categoría.
     * @return ResponseEntity con la respuesta y el código de estado HTTP.
     * @throws ApiException Si el nombre o el tag de la categoría ya están registrados.
     */
	@Override
	public ResponseEntity<ApiResponse> createCategory(DtoCategoryIn in) {
		try {
			repo.createCategory(in.getCategory(), in.getTag());
			return new ResponseEntity<>(new ApiResponse("La categoría ha sido registrada"), HttpStatus.CREATED);
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_category"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
			if (e.getLocalizedMessage().contains("ux_category"))
				throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");

			throw new DBAccessException(e);
		}
	}

    /**
     * Actualiza los datos de una categoría existente.
     * @param id Identificador único de la categoría a actualizar.
     * @param in DTO con los nuevos datos de la categoría.
     * @return ResponseEntity con la respuesta y el código de estado HTTP.
     * @throws ApiException Si el nombre o el tag ya están registrados.
     */
	@Override
	public ResponseEntity<ApiResponse> updateCategory(int id, DtoCategoryIn in) {
		try {
			validateCategoryId(id);
			repo.updateCategory(id, in.getCategory(), in.getTag());
			return new ResponseEntity<>(new ApiResponse("La categoría ha sido actualizada"), HttpStatus.OK);
		}catch (DataAccessException e) {
			if (e.getLocalizedMessage().contains("ux_categoría"))
				throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
			if (e.getLocalizedMessage().contains("ux_tag"))
				throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");

			throw new DBAccessException(e);
		}
	}

    /**
     * Habilita una categoría previamente deshabilitada.
     * @param id Identificador único de la categoría.
     * @return ResponseEntity con la respuesta y el código de estado HTTP.
     * @throws ApiException Si el ID de la categoría no existe.
     */
	@Override
	public ResponseEntity<ApiResponse> enableCategory(int id) {
		try {
			validateCategoryId(id);
			repo.enableCategory(id);
			return new ResponseEntity<>(new ApiResponse("La categoría ha sido activada"), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

    /**
     * Deshabilita una categoría activa.
     * @param id Identificador único de la categoría.
     * @return ResponseEntity con la respuesta y el código de estado HTTP.
     * @throws ApiException Si el ID de la categoría no existe.
     */
	@Override
	public ResponseEntity<ApiResponse> disableCategory(int id) {
		try {
			validateCategoryId(id);
			repo.disableCategory(id);
			return new ResponseEntity<>(new ApiResponse("La categoría ha sido desactivada"), HttpStatus.OK);
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}
	
    /**
     * Método extra y necesario.
     * Valida si el id de la categoría existe en la base de datos.
     * @param id Identificador único de la categoría.
     * @throws ApiException Si la categoría con el id dado no existe.
     */
	private void validateCategoryId(int id) {
		try {
			if(repo.getCategory(id) == null) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoria no existe");
			}
		}catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

	// Fin de la clase :)
    
}
