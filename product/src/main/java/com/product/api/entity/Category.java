package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad de la tabla "category" en la base de datos.
 */
@Entity
@Table(name = "category")
public class Category {

	// Atributos 
	
	// >>> Id <<<
	@Id
	@JsonProperty("category_id")
	@Column(name = "category_id")
	private int category_id;

	// >>> Nombre de la categoría <<<
	@JsonProperty("category")
	@Column(name = "category")
    private String category;
	
	// >>> tag de la categoría <<<
	@JsonProperty("tag")
	@Column(name = "tag")
    private String tag;
	
	// >>> Status para saber si esta activa <<<
	@JsonProperty("status")
	@Column(name = "status")
	// Por default, nosotros lo ponemos en 1.
    private int status = 1;
	
	// Contador para manejar los id y que se generen sin repeticiones.
    private static int idCounter=0;
    
    /**
     * Constructor por defecto
     */
    public Category(){
        this.category_id=++idCounter;
        this.category="Cat"+this.category_id;
        this.tag="tag"+this.category_id;
    }
    
    /**
     * Constructor con parámetros.
     * @param category, nombre de la categoría.
     * @param tag, tag de la categoría.
     */
    public Category(String category, String tag){
        this.category_id=++idCounter;
        this.category=category;
        this.tag=tag;
    }

    // Getters y setters
    
    /**
     * Regresa el ID de la categoría.
     * @return ID de la categoría.
     */
    public int getCategoryId(){
        return this.category_id;
    }

    /**
     * Regresa el nombre de la categoría.
     * @return Nombre de la categoría.
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * Regresa la etiqueta de la categoría.
     * @return Tag de la categoría.
     */
    public String getTag(){
        return this.tag;
    }


    /**
     * Regresa el estado (su Status) de la categoría.
     * @return Estado de la categoría (1: activa, 0: inactiva).
     */
    public int getStatus(){
        return this.status;
    }

    /**
     * Método extra. 
     * Cambia el estado de la categoría sin ningún parametro y evitamos que el 
     * usuario ingrese otro número que no sea 1 o 0. De todos modos, agregamos 
     * el método setStatus también.
     * Si está activa (1), la desactiva (0), y viceversa.
     */
    public void changeStatus(){
        if(this.getStatus()==1)
            this.setStatus(0);
        else
            this.setStatus(1);
    }

    /**
     * Cambia el estado (status) de la categoría.
     * @param i Nuevo estado de la categoría (1: activa, 0: inactiva).
     */
    public void setStatus(int i){
      this.status = i;
    }

    /**
     * Representación en formato de String de la categoría.
     * @return Cadena con los datos de la categoría.
     */
    @Override
    public String toString(){
        return "{"+this.getCategoryId()+","+this.getCategory()+","+this.getTag()+","+this.getStatus()+"}";
    }

    // Fin de la clase :)
}
