package com.product.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;

/**
 * +++++ Punto 1 de la práctica 6 +++++
 * 
 * Repositorio JPA para la entidad Product.
 */
@Repository
public interface RepoProduct extends JpaRepository<Product, Integer> {

	
    /**
     * Consulta personalizada para obtener el detalle completo de un producto,
     * incluyendo el nombre de la categoría asociada.
     */
	@Query(value = "SELECT p.*, c.category "
			+ "FROM product p "
			+ "INNER JOIN category c ON c.category_id = p.category_id "
			+ "WHERE p.product_id = :product_id;", nativeQuery = true)
	DtoProductOut getProduct (Integer product_id);

	
}
