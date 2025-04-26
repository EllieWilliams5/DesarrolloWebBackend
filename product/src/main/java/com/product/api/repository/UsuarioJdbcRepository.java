package com.product.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.product.api.domain.Usuario;

/**
 * Repositorio JDBC para la gestión de usuarios.
 * 
 * Carlos tiene una similar en su implementación. Cambie un par de cosas.
 *  
 * Esta clase proporciona métodos para consultar usuarios en la base de datos
 * utilizando consultas SQL manuales.
 */
@Repository
public class UsuarioJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Busca un usuario por su nombre de usuario (username).
     * 
     * @param username nombre de usuario a buscar
     * @return Optional que contiene el usuario encontrado, o vacío si no existe
     */
    public Optional<Usuario> findByUsername(String username) {
        String sql = """
            SELECT username, password, role, status
              FROM usuario
             WHERE username = ?
        """;
        
        List<Usuario> list = jdbcTemplate.query(
            sql,
            (rs, rn) -> new Usuario(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getInt("status")
            ),
            username
        );
        return list.stream().findFirst();
    }

    /**
     * Busca un usuario por nombre de usuario y contraseña encriptada.
     * 
     * @param username nombre de usuario
     * @param hashPass contraseña ya encriptada (hash BCrypt)
     * @return Optional que contiene el usuario encontrado, o vacío si no coincide
     */
    public Optional<Usuario> findByUsernameAndPassword(String username, String hashPass) {
        String sql = """
            SELECT username, password, role, status
              FROM usuario
             WHERE username = ?
               AND password = ?
        """;
        List<Usuario> list = jdbcTemplate.query(
            sql,
            (rs, rn) -> new Usuario(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getInt("status")
            ),
            username, hashPass
        );
        return list.stream().findFirst();
    }
}

