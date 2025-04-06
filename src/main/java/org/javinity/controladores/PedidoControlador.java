package org.javinity.controladores;

import org.javinity.RepositorioGenerico;
import org.javinity.excepciones.ElementoNoEncontradoException;
import org.javinity.excepciones.PedidoNoEliminableException;
import org.javinity.modelos.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador responsable de la lógica de negocio relacionada con pedidos.
 * Permite agregar, eliminar, mostrar y filtrar pedidos según su estado o cliente.
 *
 * Implementa validaciones de envío basadas en la fecha y tiempo de preparación.
 *
 * @author Javinity
 */
public class PedidoControlador {

    private RepositorioGenerico<Pedido> pedidos;

    /**
     * Constructor que inicializa el repositorio de pedidos.
     */
    public PedidoControlador() {
        this.pedidos = new RepositorioGenerico<>();
    }

    /**
     * Agrega un nuevo pedido al sistema.
     *
     * @param numPedido Número identificador del pedido.
     * @param pedido    Objeto pedido a agregar.
     */
    public void agregarPedido(int numPedido, Pedido pedido) {
        pedidos.agregar(String.valueOf(numPedido), pedido);
    }

    /**
     * Obtiene un pedido por su número.
     *
     * @param numPedido Número del pedido.
     * @return Pedido correspondiente.
     * @throws ElementoNoEncontradoException si no se encuentra el pedido.
     */
    public Pedido obtenerPedido(int numPedido) {
        Pedido pedido = pedidos.obtener(String.valueOf(numPedido));
        if (pedido == null) {
            throw new ElementoNoEncontradoException("Pedido con número " + numPedido + " no encontrado.");
        }
        return pedido;
    }

    /**
     * Elimina un pedido si aún no ha sido enviado.
     *
     * @param numPedido Número del pedido a eliminar.
     * @throws ElementoNoEncontradoException si no existe el pedido.
     * @throws PedidoNoEliminableException   si el pedido ya fue enviado.
     */
    public void eliminarPedido(int numPedido) {
        Pedido pedido = pedidos.obtener(String.valueOf(numPedido));
        if (pedido == null) {
            throw new ElementoNoEncontradoException("No se puede eliminar. Pedido con número " + numPedido + " no encontrado.");
        }

        LocalDateTime tiempoLimite = pedido.getFechaHoraPedido().plusMinutes(pedido.getArticulo().getTiempoPrepEnvio());
        if (LocalDateTime.now().isAfter(tiempoLimite)) {
            throw new PedidoNoEliminableException("No se puede eliminar. El pedido ya fue enviado.");
        }

        pedidos.eliminar(String.valueOf(numPedido));
    }

    /**
     * Muestra todos los pedidos registrados en el sistema.
     * Si no hay pedidos, muestra un mensaje informativo.
     */
    public void mostrarPedidos() {
        if (pedidos.contarElementos() == 0) {
            System.out.println("No hay pedidos registrados.");
        } else {
            pedidos.obtenerTodos().values().forEach(System.out::println);
        }
    }

    /**
     * Devuelve el número total de pedidos registrados.
     *
     * @return Número de pedidos.
     */
    public int contarPedidos() {
        return pedidos.contarElementos();
    }

    /**
     * Devuelve una lista de pedidos pendientes de un cliente específico.
     * Un pedido se considera pendiente si aún no ha pasado su tiempo de preparación.
     *
     * @param emailCliente Email del cliente.
     * @return Lista de pedidos pendientes.
     */
    public List<Pedido> obtenerPedidosPendientesPorCliente(String emailCliente) {
        return pedidos.obtenerTodos().values().stream()
                .filter(pedido -> pedido.getCliente().getEmail().equals(emailCliente))
                .filter(pedido -> LocalDateTime.now().isBefore(
                        pedido.getFechaHoraPedido().plusMinutes(pedido.getArticulo().getTiempoPrepEnvio())))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve una lista de pedidos enviados de un cliente específico.
     * Un pedido se considera enviado si ya ha pasado su tiempo de preparación.
     *
     * @param emailCliente Email del cliente.
     * @return Lista de pedidos enviados.
     */
    public List<Pedido> obtenerPedidosEnviadosPorCliente(String emailCliente) {
        return pedidos.obtenerTodos().values().stream()
                .filter(pedido -> pedido.getCliente().getEmail().equals(emailCliente))
                .filter(pedido -> LocalDateTime.now().isAfter(
                        pedido.getFechaHoraPedido().plusMinutes(pedido.getArticulo().getTiempoPrepEnvio())))
                .collect(Collectors.toList());
    }
}
