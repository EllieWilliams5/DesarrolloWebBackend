package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.service.SvcCategory;
import com.product.common.dto.ApiResponse;
import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.exception.ApiException;

import jakarta.validation.Valid;

// Controlador para gestionar las categorías.


@RestController
@RequestMapping("/category")
public class CtrlCategory {

	@Autowired
	SvcCategory svc;
	
	// Método para obtener todas las categorias (en forma de Lista) registradas
	// en la base de datos 
    @GetMapping
    public ResponseEntity<List<Category>> darCategorias() {
        return svc.getCategories();
    }
    
    // ----- Nuevos Métodos -----
    
    // +++++ Punto 5 de la práctica 5. jeje +++++
    
    
    /**
     * Regresa solamente las categorías activas (Status = 1).
     * @return Lista de categorías de Status = 1.
     */
	@GetMapping("/active")
	public ResponseEntity<List<Category>> getActiveCategories() {
		return svc.getActiveCategories();
	}
	
    /**
     * Regresa una categoría específica según su ID.
     * @param id único de la categoría.
     * @return Categoría correspondiente al id proporcionado.
     */
	@GetMapping("/{id}")
	public ResponseEntity<Category> getRegion(@PathVariable int id) {
		return svc.getCategory(id);
	}
	
    /**
     * Crea una nueva categoría en la base de datos.
     * @param in, que son los datos de entrada de la nueva categoría.
     * @param bindingResult, los resultado de la validación de los datos.
     * @return Respuesta con el estado de la operación.
     * @throws ApiException, si los datos no cumplen con los requisitos de validación.
     */
	@PostMapping
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody DtoCategoryIn in, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc.createCategory(in);
	}
	
    /**
     * Actualiza una categoría existente en la base de datos.
     * @param id único de la categoría a actualizar.
     * @param in, que son los datos actualizados de la categoría.
     * @param bindingResult, resultado de la validación de los datos.
     * @return Respuesta con el estado de la operación.
     * @throws ApiException, si los datos no cumplen con los requisitos de validación.
     */
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable int id, @Valid @RequestBody DtoCategoryIn in,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc.updateCategory(id, in);
	}
	
    /**
     * Activa una categoría deshabilitada.
     * @param id único de la categoría a habilitar.
     * @return Respuesta con el estado de la operación.
     */
	@PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enableCategory(@PathVariable int id) {
		return svc.enableCategory(id);
	}

    /**
     * Desactiva una categoría activa.
     * @param id único de la categoría a deshabilitar.
     * @return Respuesta con el estado de la operación.
     */
	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disableCategory(@PathVariable int id) {
		return svc.disableCategory(id);
	}
	
	// Fin de la clase :)
	

}
