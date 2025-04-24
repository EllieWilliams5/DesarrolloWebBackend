package com.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Manejador global de excepciones para la API.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
    /**
     * Maneja las excepciones de tipo {@link ApiException}.
     * Captura errores específicos lanzados por la aplicación y devuelve una respuesta estructurada.
     * 
     * @param exception Excepción capturada.
     * @param request Información de la solicitud HTTP en la que ocurrió el error.
     * @return ResponseEntity con el objeto {@link ExceptionResponse} y el código de estado HTTP correspondiente.
     */
	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ExceptionResponse> handleApiException(ApiException exception, WebRequest request){
		ExceptionResponse response = new ExceptionResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(exception.getStatus().value());
		response.setError(exception.getStatus());
		response.setMessage(exception.getMessage());
		response.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
		return new ResponseEntity<>(response, response.getError());
		}
	
	/**
     * Maneja las excepciones de tipo {@link DBAccessException}.
     * Captura errores relacionados con el acceso a la base de datos y devuelve una respuesta estructurada.
     * 
     * @param exception Excepción capturada.
     * @param request Información de la solicitud HTTP en la que ocurrió el error.
     * @return ResponseEntity con el objeto {@link ExceptionResponse} y el código de estado HTTP 500.
     */
	@ExceptionHandler(DBAccessException.class)
	protected ResponseEntity<ExceptionResponse> DBAccessException(DBAccessException exception, WebRequest request){

		System.out.println(exception.getException().getLocalizedMessage());
		
		ExceptionResponse response = new ExceptionResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setError(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setMessage("Error al consultar la base de datos");
		response.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		return new ResponseEntity<>(response, response.getError());
	}

	// Fin de la clase :)
}
