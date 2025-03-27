package com.product.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.service.SvcProduct;
import com.product.common.dto.ApiResponse;
import com.product.exception.ApiException;

import jakarta.validation.Valid;

/**
 * +++++ Punto 1 de la práctica 6 +++++
 * 
 * Controlador REST para la gestión de productos.
 */
@RestController
@RequestMapping("/product")
public class CtrlProduct {

    // Inyección del servicio que tiene la logica para productos
	@Autowired
	SvcProduct svc;

    /**
     * Endpoint: GET /product
     * Consulta todos los productos disponibles.
     */
	@GetMapping
	public ResponseEntity<List<DtoProductListOut>> getProducts() {
		return svc.getProducts();
	}

    /**
     * Endpoint: GET /product/{id}
     * Consulta el detalle de un producto, incluyendo sus imágenes en base 64.
     */
	@GetMapping("/{id}")
	public ResponseEntity<DtoProductOut> getProduct(@PathVariable Integer id) {
		return svc.getProduct(id);
	}


    /**
     * Endpoint: POST /product
     * Crea un nuevo producto.
     * Valida que los datos cumplan las restricciones anotadas en el dto.
     */
	@PostMapping
	public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody DtoProductIn in, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc.createProduct(in);
	}

	   /**
     * Endpoint: PUT /product/{id}
     * Actualiza un producto existente con nuevos datos.
     */
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @Valid @RequestBody DtoProductIn in,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc.updateProduct(id, in);
	}
	
    /**
     * Endpoint: PATCH /product/{id}/enable
     * Activa un producto.
     */
	@PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enableProduct(@PathVariable Integer id) {
		return svc.enableProduct(id);
	}


    /**
     * Endpoint: PATCH /product/{id}/disable
     * Desactiva un producto.
     */
	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disableProduct(@PathVariable Integer id) {
		return svc.disableProduct(id);
	}
}
