package org.javinity.dao.impl;

import org.javinity.dao.interfaces.ArticuloDAO;
import org.javinity.modelos.Articulo;

import java.util.List;

public class ArticuloImpl implements ArticuloDAO {

    @Override
    public void insertar(Articulo articulo) {

    }

    @Override
    public Articulo buscar(String codigo) {
        return null;
    }

    @Override
    public List<Articulo> listar() {
        return List.of();
    }

    @Override
    public void eliminar(String codigo) {

    }
}
