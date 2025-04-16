package org.javinity.controladores;

import org.javinity.dao.interfaces.PedidoDAO;
import org.javinity.excepciones.ElementoNoEncontradoException;
import org.javinity.excepciones.PedidoNoEliminableException;
import org.javinity.modelos.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador responsable de la lógica de negocio relacionada con los pedidos.
 * Permite agregar, eliminar, mostrar y filtrar pedidos según su estado o cliente.
 * Aplica reglas de negocio como la validación del tiempo de preparación para permitir su eliminación.
 *
 * Forma parte de la capa de control del patrón MVC.
 * Utiliza una interfaz PedidoDAO para realizar operaciones sobre la base de datos.
 *
 * @author Javinity
 */
public class PedidoControlador {

    private final PedidoDAO pedidoDAO;

    /**
     * Constructor que recibe una implementación de PedidoDAO.
     *
     * @param pedidoDAO Implementación concreta del acceso a datos de pedidos.
     */
    public PedidoControlador(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    /**
     * Agrega un nuevo pedido a la base de datos.
     *
     * @param pedido Pedido a agregar.
     */
    public void agregarPedido(Pedido pedido) {
        pedidoDAO.insertar(pedido);
    }

    /**
     * Elimina un pedido si aún no ha sido enviado (según el tiempo de preparación).
     *
     * @param numPedido Número del pedido a eliminar.
     * @throws ElementoNoEncontradoException Si el pedido no existe.
     * @throws PedidoNoEliminableException Si el pedido ya fue enviado.
     */
    public void eliminarPedido(int numPedido) {
        Pedido pedido = pedidoDAO.buscar(numPedido);
        if (pedido == null) {
            throw new ElementoNoEncontradoException("Pedido no encontrado.");
        }

        LocalDateTime tiempoLimite = pedido.getFechaHoraPedido()
                .plusMinutes(pedido.getArticulo().getTiempoPrepEnvio());

        if (LocalDateTime.now().isAfter(tiempoLimite)) {
            throw new PedidoNoEliminableException("El pedido ya fue enviado y no puede eliminarse.");
        }

        pedidoDAO.eliminar(numPedido);
    }

    /**
     * Recupera todos los pedidos registrados en la base de datos.
     *
     * @return Lista completa de pedidos.
     */
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoDAO.listar();
    }

    /**
     * Devuelve la cantidad total de pedidos registrados.
     *
     * @return Número total de pedidos.
     */
    public int contarPedidos() {
        return pedidoDAO.listar().size();
    }

    /**
     * Devuelve los pedidos pendientes (no enviados) de un cliente específico.
     *
     * @param emailCliente Email del cliente.
     * @return Lista de pedidos pendientes.
     */
    public List<Pedido> obtenerPedidosPendientesPorCliente(String emailCliente) {
        return pedidoDAO.listar().stream()
                .filter(p -> p.getCliente().getEmail().equalsIgnoreCase(emailCliente))
                .filter(p -> LocalDateTime.now().isBefore(
                        p.getFechaHoraPedido().plusMinutes(p.getArticulo().getTiempoPrepEnvio())))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve los pedidos ya enviados de un cliente específico.
     *
     * @param emailCliente Email del cliente.
     * @return Lista de pedidos enviados.
     */
    public List<Pedido> obtenerPedidosEnviadosPorCliente(String emailCliente) {
        return pedidoDAO.listar().stream()
                .filter(p -> p.getCliente().getEmail().equalsIgnoreCase(emailCliente))
                .filter(p -> LocalDateTime.now().isAfter(
                        p.getFechaHoraPedido().plusMinutes(p.getArticulo().getTiempoPrepEnvio())))
                .collect(Collectors.toList());
    }
}
