package org.javinity.dao;

import jakarta.persistence.EntityManager;

import org.javinity.modelos.Articulo;



import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de Articulo usando JPA.
 * @author Javinity
 */
public class ArticuloDAOImpl{

    private EntityManager em;

    public ArticuloDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Inserta un nuevo artículo en la base de datos.
     *
     * @param articulo Artículo a insertar.
     */
    public void insertar(Articulo articulo) {
        em.getTransaction().begin();
        em.persist(articulo);
        em.getTransaction().commit();
    }

    /**
     * Busca un artículo en la base de datos por su código.
     *
     * @param codigo Código del artículo.
     * @return Objeto Articulo si existe, null si no se encuentra.
     */
    public Articulo buscar(String codigo) {

        Articulo articulo = em.find(Articulo.class, codigo );
        return articulo;
    }

    /**
     * Obtiene todos los artículos almacenados en la base de datos.
     *
     * @return Lista de artículos.
     */
    public List<Articulo> listar() {
        List<Articulo> articulos = new ArrayList<>();
        return em.createQuery("SELECT a FROM Articulo a", Articulo.class).getResultList();

  }

    /**
     * Elimina un artículo de la base de datos por su código.
     *
     * @param codigo Código del artículo a eliminar.
     */
    public void eliminar(String codigo) {
        // Eliminar si ya existe (opcional)
        Articulo existente = this.buscar(codigo);
        if (existente != null) {
            em.getTransaction().begin();
            em.remove(existente);
            em.getTransaction().commit();
        }

    }
}

