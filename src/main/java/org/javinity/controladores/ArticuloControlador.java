package org.javinity.controladores;


import org.javinity.RepositorioGenerico;
import org.javinity.excepciones.ElementoNoEncontradoException;
import org.javinity.modelos.Articulo;

/**
 * Controlador encargado de gestionar la lógica de negocio relacionada con los artículos.
 * Sirve de intermediario entre la vista y el modelo.
 * Utiliza un repositorio genérico para almacenar los artículos temporalmente.
 *
 * @author Javinity
 */
public class ArticuloControlador {
    private RepositorioGenerico<Articulo> articulos;

    /**
     * Constructor que inicializa el repositorio de artículos.
     */
    public ArticuloControlador() {
        this.articulos = new RepositorioGenerico<>();
    }


    /**
     * Agrega un artículo al repositorio si no existe ya un artículo con el mismo código.
     *
     * @param codigo   Código del artículo.
     * @param articulo Objeto artículo que se desea agregar.
     * @throws IllegalArgumentException si ya existe un artículo con el mismo código.
     */
    public void agregarArticulo(String codigo, Articulo articulo) {
        if (articulos.obtener(codigo) != null) {
            throw new IllegalArgumentException("El artículo con código " + codigo + " ya existe.");
        }
        articulos.agregar(codigo, articulo);
    }


    /**
     * Obtiene un artículo por su código.
     *
     * @param codigo Código del artículo.
     * @return El artículo correspondiente al código.
     * @throws ElementoNoEncontradoException si el artículo no existe.
     */
    public Articulo obtenerArticulo(String codigo) {
        Articulo articulo = articulos.obtener(codigo);
        if (articulo == null) {
            throw new ElementoNoEncontradoException("Artículo con código " + codigo + " no encontrado.");
        }
        return articulo;
    }


    /**
     * Elimina un artículo por su código.
     *
     * @param codigo Código del artículo a eliminar.
     * @throws ElementoNoEncontradoException si el artículo no existe.
     */
    public void eliminarArticulo(String codigo) {
        if (articulos.obtener(codigo) == null) {
            throw new ElementoNoEncontradoException("No se puede eliminar. Artículo con código " + codigo + " no encontrado.");
        }
        articulos.eliminar(codigo);
    }


    /**
     * Muestra todos los artículos almacenados en el repositorio.
     */
    public void mostrarArticulos() {
        articulos.mostrarTodos();
    }

}
