package org.javinity.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.ClienteEstandar;
import org.javinity.modelos.ClientePremium;
import java.util.List;

/**
 * Implementación del DAO para la entidad Cliente usando JPA (Hibernate).
 * Se encarga de las operaciones CRUD sobre la tabla de clientes.
 *
 * Esta clase utiliza un EntityManager inyectado desde fuera, lo que permite
 * reutilizar la conexión en varias operaciones consecutivas.
 * @author Javinity
 */
public class ClienteDAOImpl  {

    private final EntityManager em;

    /**
     * Constructor que recibe el EntityManager.
     *
     * @param em EntityManager para manejar la persistencia con JPA.
     */
    public ClienteDAOImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * Inserta un cliente en la base de datos con JPA(Hibernate).
     *
     * @param cliente El cliente a insertar.
     */
    public void insertar(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e){
            em.getTransaction().rollback();
            System.out.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca un cliente por su email.
     *
     * @param email Email del cliente.
     * @return Objeto Cliente si se encuentra, null si no existe.
     */
    public Cliente buscar(String email) {
        try{
            return em.find(Cliente.class, email);
        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todos los clientes registrados.
     *
     * @return Lista de clientes existentes en la base de datos.
     */
    public List<Cliente> listar() {
        try{
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " +e.getMessage());
            return List.of();
        }
    }

    /**
     * Elimina un cliente por su email.
     *
     * @param email Email del cliente a eliminar.
     */
    public void eliminar(String email){
        try{
            Cliente cliente = buscar(email);
            if(cliente != null){
                em.getTransaction().begin();
                em.remove(cliente);
                em.getTransaction().commit();
            }
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al eliminar cliente: " +e.getMessage());
        }
    }

    /**
     * Crea un objeto Cliente concreto a partir del tipo indicado.
     * Este metodo es de uso interno para crear instancias específicas..
     *
     * @param email     Email del cliente.
     * @param nombre    Nombre del cliente.
     * @param domicilio Domicilio del cliente.
     * @param nif       NIF del cliente.
     * @param tipo      Tipo de cliente ("premium" o "estandar").
     * @return Instancia de ClientePremium o ClienteEstandar.
     */
    private Cliente construirCliente(String email, String nombre, String domicilio, String nif, String tipo) {
        return "premium".equalsIgnoreCase(tipo)
                ? new ClientePremium(email, nombre, domicilio, nif)
                : new ClienteEstandar(email, nombre, domicilio, nif);
    }
}
