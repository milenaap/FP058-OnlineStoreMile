package org.javinity.dao.interfaces;

import org.javinity.modelos.Articulo;

import java.util.List;
/**
 * Interfaz DAO para acceder a datos de artículos en la base de datos.
 * Define las operaciones básicas de persistencia que deben implementarse.
 */
public interface ArticuloDAO {
    /**
     * Inserta un nuevo artículo en la base de datos.
     *
     * @param articulo el artículo que se desea insertar
     */
    void insertar(Articulo articulo);

    /**
     * Busca un artículo en la base de datos a partir de su código.
     *
     * @param codigo el código único del artículo
     * @return el artículo encontrado, o null si no existe
     */
    Articulo buscar(String codigo);

    /**
     * Lista todos los artículos almacenados en la base de datos.
     *
     * @return una lista con todos los artículos disponibles
     */
    List<Articulo> listar();

    /**
     * Elimina un artículo de la base de datos según su código.
     *
     * @param codigo el código del artículo a eliminar
     */
    void eliminar(String codigo);
}
