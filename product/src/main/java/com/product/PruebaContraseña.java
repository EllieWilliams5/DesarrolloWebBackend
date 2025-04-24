package com.product;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PruebaContraseña {
	  public static void main(String[] args) {
		    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		    String rawPassword = "tuContraseñaSegura";
		    String hash = encoder.encode(rawPassword);
		    System.out.println(hash);
		  }
}
