package com.product.api.repository;

import java.util.List;

import com.product.api.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;


/**
 * Repositorio para la entidad Category.
 */
// Deberia poner Integer en vez de int?...
@Repository
public interface RepoCategory extends JpaRepository<Category, Integer>{
	
    
    /**
     * Regresa todas las categorías ordenadas alfabéticamente.
     * @return Lista de todas las categorías.
     */
    @Query(value ="SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> getCategories();
    
    // +++++ Parte 5 de la práctica 5. jeje +++++
    
    /**
     * Regresa solo las categorías activas (status = 1), ordenadas alfabéticamente.
     * @return Lista de categorías activas.
     */
	@Query(value = "SELECT * FROM category WHERE status = 1 ORDER BY category", nativeQuery = true)
	List<Category> getActiveCategories();

    /**
     * Regresa una categoría específica por su id.
     * @param category_id Identificador de la categoría.
     * @return La categoría correspondiente al id dado.
     */
	@Query(value = "SELECT * FROM category WHERE category_id = :category_id ORDER BY category", nativeQuery = true)
	Category getCategory(@Param("category_id") int category_id);

	// Posible falla?...
	// Espero que no...
	
    /**
     * Inserta una nueva categoría en la base de datos.
     * @param category, Nombre de la categoría.
     * @param tag, Tag de la categoría.
     */
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO category (category, tag) VALUES (:category, :tag)", nativeQuery = true)
	void createCategory(@Param("category") String category, @Param("tag") String tag);
	
    /**
     * Actualiza los datos de una categoría existente.
     * @param category_id, id de la categoría a actualizar.
     * @param category, Nuevo nombre de la categoría.
     * @param tag, Nuevo tag de la categoría.
     */
	@Modifying
	@Transactional
	@Query(value = "UPDATE category SET category = :category, tag = :tag WHERE category_id = :category_id;", nativeQuery = true)
	void updateCategory(@Param("category_id") int category_id, @Param("category") String category, @Param("tag") String tag);

    /**
     * Habilita una categoría estableciendo su estado (status) en 1.
     * @param category_id, id de la categoría a habilitar.
     */
	@Modifying
	@Transactional
	@Query(value = "UPDATE category SET status = 1 WHERE category_id = :category_id;", nativeQuery = true)
	void enableCategory(@Param("category_id") int category_id);

    /**
     * Deshabilita una categoría estableciendo su estado en 0.
     * @param category_id ID de la categoría a deshabilitar.
     */
	@Modifying
	@Transactional
	@Query(value = "UPDATE category SET status = 0 WHERE category_id = :category_id;", nativeQuery = true)
	void disableCategory(@Param("category_id") int category_id);
	
	// Fin de la clase :)

}