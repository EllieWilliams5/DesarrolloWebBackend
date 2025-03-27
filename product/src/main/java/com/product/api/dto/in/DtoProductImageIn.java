package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

/**
 * +++++ Punto 2 de la práctica 6 +++++
 *
  * DTO para la entrada de datos al registrar una imagen de producto.
 */
public class DtoProductImageIn {

	// Atributos 
	
	// id del producto que se asociará con la imagén
	@JsonProperty("product_id")
	@NotNull(message="El product_id es obligatorio")
	private Integer product_id;
	
	// String de la imagen en base 64
	@JsonProperty("image")
	@NotNull(message="El image es obligatorio")
	private String image;
	
	// Getters y Setters

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
