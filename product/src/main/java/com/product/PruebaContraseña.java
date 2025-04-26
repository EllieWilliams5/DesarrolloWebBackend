package com.product;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase auxiliar para generar un hash BCrypt de una contraseña en texto plano.
 * Carlos tenia algo similar, pero preferi hacer una clase exclusiva.
 */
public class PruebaContraseña {
	
	  public static void main(String[] args) {
		  
		    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		    String rawPassword = "Contraseña a Encriptar";
		    
		    String hash = encoder.encode(rawPassword);
		    //Resultado final.
		    System.out.println(hash);
		    
		  }
}
