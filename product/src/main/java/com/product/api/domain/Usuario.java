package com.product.api.domain;

import java.io.Serializable;

/**
 * Clase de dominio que representa un registro de la tabla 'usuario'.
 * 
 * Carlos tiene una similar en su implementación. Cambie un par de cosas.
 * 
 * Esta clase se utiliza para mapear datos de usuarios consultados desde
 * la base de datos mediante JDBC.
 */
public class Usuario implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;  
    private String role;      
    private int status;      

    public Usuario(String username, String password, String role, int status) {
        this.username = username;
        this.password = password;
        this.role     = role;
        this.status   = status;
    }

    // Getters
    public String getUsername(){
    	return username;
    }
    
    public String getPassword(){
    	return password; 
    }
    
    public String getRole(){ 
    	return role; 
    }
    
    public int getStatus(){
    	return status;
    }
    
}
