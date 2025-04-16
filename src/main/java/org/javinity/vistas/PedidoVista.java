package org.javinity.vistas;

import org.javinity.controladores.ArticuloControlador;
import org.javinity.controladores.ClienteControlador;
import org.javinity.controladores.PedidoControlador;
import org.javinity.excepciones.ElementoNoEncontradoException;
import org.javinity.excepciones.PedidoNoEliminableException;
import org.javinity.modelos.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Vista encargada de gestionar la interacción por consola relacionada con los pedidos.
 * Permite agregar, eliminar, mostrar y filtrar pedidos por estado o cliente.
 * Forma parte del patrón MVC (capa vista).
 *
 * @author Javinity
 */
public class PedidoVista {

    private final PedidoControlador pedidoControlador;
    private final ClienteControlador clienteControlador;
    private final ArticuloControlador articuloControlador;
    private final Scanner scanner;

    public PedidoVista(PedidoControlador pedidoControlador,
                       ClienteControlador clienteControlador,
                       ArticuloControlador articuloControlador) {
        this.pedidoControlador = pedidoControlador;
        this.clienteControlador = clienteControlador;
        this.articuloControlador = articuloControlador;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal para la gestión de pedidos.
     */
    public void mostrarMenu() {
        int opcion = 0;
        do {
            System.out.println("\nGestión de Pedidos");
            System.out.println("1. Añadir Pedido");
            System.out.println("2. Eliminar Pedido");
            System.out.println("3. Mostrar TODOS los Pedidos");
            System.out.println("4. Mostrar Pedidos Pendientes por Cliente");
            System.out.println("5. Mostrar Pedidos Enviados por Cliente");
            System.out.println("6. Volver al menú principal");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> agregarPedido();
                    case 2 -> eliminarPedido();
                    case 3 -> mostrarTodosLosPedidos();
                    case 4 -> mostrarPedidosPendientesPorCliente();
                    case 5 -> mostrarPedidosEnviadosPorCliente();
                    case 6 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingresa un número válido.");
            }
        } while (opcion != 6);
    }

    /**
     * Permite agregar un nuevo pedido y registrar al cliente si no existe.
     */
    private void agregarPedido() {
        try {
            System.out.print("Email del cliente: ");
            String email = scanner.nextLine();
            Cliente cliente = clienteControlador.obtenerCliente(email);

            if (cliente == null) {
                System.out.println("Cliente no encontrado. Vamos a registrarlo.");
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("Domicilio: ");
                String domicilio = scanner.nextLine();
                System.out.print("NIF: ");
                String nif = scanner.nextLine();
                System.out.print("¿Tipo de cliente (1. Estándar | 2. Premium)?: ");
                int tipo = Integer.parseInt(scanner.nextLine());

                cliente = (tipo == 2)
                        ? new ClientePremium(email, nombre, domicilio, nif)
                        : new ClienteEstandar(email, nombre, domicilio, nif);

                clienteControlador.agregarCliente(cliente);
                System.out.println("Cliente registrado correctamente.");
            }

            System.out.print("Código del artículo: ");
            String codigo = scanner.nextLine();
            Articulo articulo = articuloControlador.obtenerArticulo(codigo);

            if (articulo == null) {
                System.out.println("Artículo no encontrado. No se puede crear el pedido.");
                return;
            }

            System.out.print("Cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine());
            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor que cero.");
                return;
            }

            Pedido nuevoPedido = new Pedido(0, cliente, articulo, cantidad, LocalDateTime.now());
            pedidoControlador.agregarPedido(nuevoPedido);
            System.out.println("Pedido agregado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar el pedido: " + e.getMessage());
        }
    }

    /**
     * Elimina un pedido si cumple la condición de no haber sido enviado.
     */
    private void eliminarPedido() {
        try {
            System.out.print("Número del pedido a eliminar: ");
            int numPedido = Integer.parseInt(scanner.nextLine());
            pedidoControlador.eliminarPedido(numPedido);
            System.out.println("Pedido eliminado correctamente.");
        } catch (ElementoNoEncontradoException | PedidoNoEliminableException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los pedidos registrados.
     */
    private void mostrarTodosLosPedidos() {
        System.out.println("Lista de TODOS los pedidos:");
        List<Pedido> pedidos = pedidoControlador.obtenerTodosLosPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            pedidos.forEach(System.out::println);
        }
    }

    /**
     * Muestra los pedidos pendientes de envío filtrados por cliente.
     */
    private void mostrarPedidosPendientesPorCliente() {
        System.out.print("Email del cliente: ");
        String email = scanner.nextLine();
        List<Pedido> pendientes = pedidoControlador.obtenerPedidosPendientesPorCliente(email);

        if (pendientes.isEmpty()) {
            System.out.println("No hay pedidos pendientes para este cliente.");
        } else {
            System.out.println("Pedidos pendientes:");
            pendientes.forEach(System.out::println);
        }
    }

    /**
     * Muestra los pedidos ya enviados filtrados por cliente.
     */
    private void mostrarPedidosEnviadosPorCliente() {
        System.out.print("Email del cliente: ");
        String email = scanner.nextLine();
        List<Pedido> enviados = pedidoControlador.obtenerPedidosEnviadosPorCliente(email);

        if (enviados.isEmpty()) {
            System.out.println("No hay pedidos enviados para este cliente.");
        } else {
            System.out.println("Pedidos enviados:");
            enviados.forEach(System.out::println);
        }
    }
}
