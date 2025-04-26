package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.service.SvcProductImage;
import com.product.common.dto.ApiResponse;
import com.product.exception.ApiException;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

/**
 * +++++ Punto 2 de la práctica 6 +++++
 * 
 * Controlador REST para la gestión de imágenes de productos.
 */
@RestController
@RequestMapping("/product-image")
@Tag(name = "product image", description = "Carga de imágenes de productos")
public class CtrlProductImage {

    // Inyección del servicio que tiene la logica para imagenes
	@Autowired
	SvcProductImage svc;

    /**
     * Endpoint: POST /product-image
     * Registra una nueva imagen para un producto.
     * Se valida que los campos requeridos estén presentes.
     */
	@PostMapping
	@Operation(summary = "Registra una imagen", description = "Guarda una imagen en formato base64 y la asocia a un producto.")
    public ResponseEntity<ApiResponse> createProductImage(@Valid @RequestBody DtoProductImageIn in, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
        	throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        return svc.createProductImage(in);
    }
	
    /**
     * Endpoint: DELETE /product-image/{id}
     * Elimina una imagen asociada a un producto (archivo + registro).
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una imagen", description = "Elimina una imagen asociada a un producto del sistema y la base de datos.")
    public ResponseEntity<ApiResponse> deleteProductImage(@PathVariable Integer id) {
        return svc.deleteProductImage(id);
    }
}
