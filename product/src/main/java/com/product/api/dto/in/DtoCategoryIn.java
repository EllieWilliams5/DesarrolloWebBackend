package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para la creación y actualización de categorías.
 */
public class DtoCategoryIn {
	
	// Atributos 
	
    /**
     * Nombre de la categoría.
     * No puede ser nulo. Lanza un mensaje de error si llega a serlo.
     */
	@JsonProperty("category")
	@NotNull(message = "La región es obligatoria. Por favor :)")
	private String category;

    /**
     * tag asociado a la categoría.
     * No puede ser nulo. Lanza un mensaje de error si llega a serlo.
     */
	@JsonProperty("tag")
	@NotNull(message = "El tag es obligatorio. Por favor :)")
	private String tag;
	
	// >>>>> Getters y Setters <<<<<

    /**
     * Obtienes el nombre de la categoría.
     * @return Nombre de la categoría.
     */
	public String getCategory() {
		return category;
	}

    /**
     * Cambia el nombre de la categoría.
     * @param category, nuevo nombre de la categoría.
     */
	public void setCategory(String category) {
		this.category = category;
	}

    /**
     * Obtiene el tag asociado a la categoría.
     * @return Tag de la categoría.
     */
	public String getTag() {
		return tag;
	}

	/**
     * Cambia el tag asociado a la categoría.
     * @param tag, nuevo tag de la categoría.
     */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	// Fin de la clase :)
	
}
