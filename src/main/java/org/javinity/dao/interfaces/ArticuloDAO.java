package org.javinity.dao.interfaces;

import org.javinity.modelos.Articulo;

import java.util.List;

public interface ArticuloDAO {
    void insertar(Articulo articulo);
    Articulo buscar(String codigo);
    List<Articulo> listar();
    void eliminar(String codigo);
}
