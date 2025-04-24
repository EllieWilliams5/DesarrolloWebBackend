package com.product.exception;

import org.springframework.dao.DataAccessException;

/**
 * Excepción personalizada para manejar errores de acceso a la base de datos.
 */
public class DBAccessException extends RuntimeException{
	

	private static final long serialVersionUID = 1L;
	
	private DataAccessException exception;
	
    /**
     * Constructor.
     */
	public DBAccessException(DataAccessException e) {
		this.exception = e;
	}

	// Get y Set
	
    /**
     * Regresa la excepción de acceso a datos encapsulada.
     * @return Excepción original de tipo {@link DataAccessException}.
     */
	public DataAccessException getException() {
		return exception;
	}

    /**
     * Establece una nueva excepción de acceso a datos.
     * @param exception Excepción de acceso a la base de datos.
     */
	public void setException(DataAccessException exception) {
		this.exception = exception;
	}

    /**
     * Obtiene el identificador de serialización de la clase.
     * @return Identificador de serialización.
     */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
