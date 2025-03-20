package com.product.common.dto;

/**
 * +++++ Punto 3 de la práctica 5 +++++
 * DTO para enviar respuestas simples en la API.
 */
public class ApiResponse {

	// Respueta
	private String message;
	

    /**
     * Constructor
     */
	public ApiResponse(String message) {
		super();
		this.message = message;
	}

	// Get y Set
	
    /**
     * Regresa el mensaje de la respuesta.
     * @return Mensaje de la API.
     */
	public String getMessage() {
		return message;
	}


    /**
     * Establece un nuevo mensaje en la respuesta.
     * @param message Mensaje a ser actualizado.
     */
	public void setMessage(String message) {
		this.message = message;
	}

	// Fin de la clase :)
}
