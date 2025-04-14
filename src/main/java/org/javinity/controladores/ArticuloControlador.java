package org.javinity.controladores;

import org.javinity.dao.interfaces.ArticuloDAO;
import org.javinity.excepciones.ElementoNoEncontradoException;
import org.javinity.modelos.Articulo;

import java.util.List;

/**
 * Controlador encargado de gestionar la lógica de negocio relacionada con los artículos.
 * Se comunica con la capa DAO para acceder a los datos y con la vista para responder a acciones del usuario.
 */
public class ArticuloControlador {

    private final ArticuloDAO articuloDAO;

    /**
     * Constructor del controlador.
     *
     * @param articuloDAO Objeto DAO que permite la persistencia de artículos.
     */
    public ArticuloControlador(ArticuloDAO articuloDAO) {
        this.articuloDAO = articuloDAO;
    }

    /**
     * Agrega un nuevo artículo si no existe uno con el mismo código en la base de datos.
     *
     * @param articulo Artículo a agregar.
     * @throws IllegalArgumentException Si ya existe un artículo con el mismo código.
     */
    public void agregarArticulo(Articulo articulo) {
        if (articuloDAO.buscar(articulo.getCodigoProducto()) != null) {
            throw new IllegalArgumentException("El artículo con código " + articulo.getCodigoProducto() + " ya existe.");
        }
        articuloDAO.insertar(articulo);
    }

    /**
     * Obtiene un artículo por su código.
     *
     * @param codigo Código del artículo a buscar.
     * @return El artículo encontrado.
     * @throws ElementoNoEncontradoException Si no se encuentra ningún artículo con ese código.
     */
    public Articulo obtenerArticulo(String codigo) {
        Articulo articulo = articuloDAO.buscar(codigo);
        if (articulo == null) {
            throw new ElementoNoEncontradoException("Artículo con código " + codigo + " no encontrado.");
        }
        return articulo;
    }

    /**
     * Elimina un artículo de la base de datos por su código.
     *
     * @param codigo Código del artículo a eliminar.
     */
    public void eliminarArticulo(String codigo) {
        articuloDAO.eliminar(codigo);
    }

    /**
     * Devuelve una lista con todos los artículos registrados en la base de datos.
     *
     * @return Lista de artículos.
     */
    public List<Articulo> obtenerTodos() {
        return articuloDAO.listar();
    }
}
