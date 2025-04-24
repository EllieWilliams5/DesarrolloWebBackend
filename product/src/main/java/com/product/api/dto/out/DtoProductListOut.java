package com.product.api.dto.out;

/**
 * +++++ Punto 2 de la práctica 6 +++++
 *
 * DTO de salida para representar un producto en listados.
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoProductListOut {
	
	// Atributos 
	
	// id del product
	@JsonProperty("product_id")
	private Integer product_id;
	
	// codigo de barras
	@JsonProperty("gtin")
	private String gtin;

	// Su nombre
	@JsonProperty("product")
	private String product;

	// Su precio 
	@JsonProperty("price")
	private Float price;

	// El estado del producto
	@JsonProperty("status")
	private Integer status;

    /**
     * Constructor con todos los campos requeridos para construir la respuesta
     * al listar productos.
     */	
	public DtoProductListOut(Integer product_id, String gtin, String product, Float price, Integer status) {
		super();
		this.product_id = product_id;
		this.gtin = gtin;
		this.product = product;
		this.price = price;
		this.status = status;
	}

	// Getteres y Setters
	
	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public String getGtin() {
		return gtin;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
