package org.javinity.dao;
import jakarta.persistence.EntityManager;
import org.javinity.modelos.Pedido;
import java.util.List;

/**
 * Implementación del DAO para la entidad Pedido usando JPA (Hibernate).
 * Gestiona las operaciones de inserción, eliminación y consulta de pedidos.
 *
 * Forma parte del patrón DAO para separar la lógica de persistencia.
 * Utiliza un EntityManager proporcionado desde el controlador o clase principal.
 *
 * @author Javinity
 */
public class PedidoDAOImpl {


    private EntityManager em;
    /**
     * Constructor que recibe el EntityManager.
     *
     * @param em EntityManager para manejar operaciones JPA.
     */
    public PedidoDAOImpl(EntityManager em){
        this.em = em;
    }


    /**
     * Inserta un nuevo pedido en la base de datos.
     *
     * @param pedido El pedido a insertar.
     */
    public void insertar(Pedido pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    /**
     * Lista todos los pedidos almacenados en la base de datos.
     *
     * @return Lista completa de pedidos.
     */
    public List<Pedido> listar() {
        return em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
    }

    /**
     * Busca un pedido en base a su número identificador.
     *
     * @param numPedido Número del pedido.
     * @return Pedido si se encuentra, null si no existe.
     */
    public Pedido buscar(int numPedido) {
        return em.find(Pedido.class, numPedido);
    }

    /**
     * Elimina un pedido de la base de datos.
     *
     * @param numPedido Número del pedido a eliminar.
     */
    public void eliminar(int numPedido) {
        Pedido pedido = buscar(numPedido);
        if(pedido!= null){
            em.getTransaction().begin();
            em.remove(pedido);
            em.getTransaction().commit();
        }
    }
}
