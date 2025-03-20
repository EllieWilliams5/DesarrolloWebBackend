package com.product.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción personalizada para manejar errores dentro de la API.
 */
public class ApiException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	
	 /**
     * Constructor de la excepción personalizada.
     * @param status Código de estado HTTP asociado al error.
     * @param message Mensaje descriptivo del error.
     */
	public ApiException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
	
	// Get y Set
	
    /**
     * Regresa el código de estado HTTP asociado a la excepción.
     * @return Código de estado HTTP.
     */
	public HttpStatus getStatus() {
		return status;
	}
	
	/**
     * Establece un nuevo código de estado HTTP para la excepción.
     * @param status Nuevo código de estado HTTP.
     */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	/**
     * Obtiene el identificador de serialización de la clase.
     * @return Identificador de serialización.
     */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	// Fin de la clase :)
	
}
