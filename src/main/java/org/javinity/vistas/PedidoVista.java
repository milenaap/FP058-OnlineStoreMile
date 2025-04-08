package org.javinity.vistas;

import org.javinity.controladores.ArticuloControlador;
import org.javinity.controladores.ClienteControlador;
import org.javinity.controladores.PedidoControlador;
import org.javinity.excepciones.ElementoNoEncontradoException;
import org.javinity.excepciones.PedidoNoEliminableException;
import org.javinity.modelos.Articulo;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.Pedido;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Vista encargada de gestionar la interacción por consola
 * relacionada con los pedidos.
 * Permite crear, eliminar y visualizar pedidos según diferentes criterios.
 *
 * Forma parte del patrón MVC y actúa como interfaz del usuario.
 *
 * @author Javinity
 */
public class PedidoVista {

    private PedidoControlador pedidoControlador;
    private ClienteControlador clienteControlador;
    private ArticuloControlador articuloControlador;
    private Scanner scanner;

    /**
     * Constructor que inicializa la vista de pedidos con los controladores necesarios.
     *
     * @param pedidoControlador   Controlador de pedidos.
     * @param clienteControlador  Controlador de clientes.
     * @param articuloControlador Controlador de artículos.
     */
    public PedidoVista(PedidoControlador pedidoControlador, ClienteControlador clienteControlador, ArticuloControlador articuloControlador) {
        this.pedidoControlador = pedidoControlador;
        this.clienteControlador = clienteControlador;
        this.articuloControlador = articuloControlador;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal de gestión de pedidos y permite al usuario
     * navegar entre las diferentes opciones.
     */
    public void mostrarMenu() {
        int opcion;
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
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1 -> agregarPedido();
                    case 2 -> eliminarPedido();
                    case 3 -> mostrarTodosLosPedidos();
                    case 4 -> mostrarPedidosPendientesPorCliente();
                    case 5 -> mostrarPedidosEnviadosPorCliente();
                    case 6 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida, intenta de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingresa un número válido.");
                scanner.nextLine(); // Limpiar buffer
                opcion = -1;
            }

        } while (opcion != 6);
    }

    /**
     * Solicita los datos para registrar un nuevo pedido y lo envía al controlador.
     */
    private void agregarPedido() {
        System.out.print("Email del cliente: ");
        Cliente cliente = clienteControlador.obtenerCliente(scanner.nextLine());
        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.print("Código del artículo: ");
        Articulo articulo = articuloControlador.obtenerArticulo(scanner.nextLine());
        if (articulo == null) {
            System.out.println("Artículo no encontrado.");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a 0.");
            return;
        }

        int nuevoNumeroPedido = pedidoControlador.contarPedidos() + 1;
        Pedido pedido = new Pedido(nuevoNumeroPedido, cliente, articulo, cantidad, LocalDateTime.now());
        pedidoControlador.agregarPedido(nuevoNumeroPedido, pedido);

        System.out.println("Pedido agregado con éxito.");
    }

    /**
     * Muestra todos los pedidos almacenados.
     */
    private void mostrarTodosLosPedidos() {
        System.out.println("==========================================");
        System.out.println("Listado de TODOS los pedidos:");
        System.out.println("==========================================");
        try {
            pedidoControlador.mostrarPedidos();
        } catch (Exception e) {
            System.out.println("Error al mostrar los pedidos: " + e.getMessage());
        }
    }

    /**
     * Solicita un email y muestra todos los pedidos pendientes del cliente.
     */
    private void mostrarPedidosPendientesPorCliente() {
        System.out.print("Ingrese el email del cliente: ");
        String email = scanner.nextLine();
        List<Pedido> pedidosPendientes = pedidoControlador.obtenerPedidosPendientesPorCliente(email);

        if (pedidosPendientes.isEmpty()) {
            System.out.println("No hay pedidos pendientes para este cliente.");
        } else {
            pedidosPendientes.forEach(System.out::println);
        }
    }

    /**
     * Solicita un email y muestra todos los pedidos enviados del cliente.
     */
    private void mostrarPedidosEnviadosPorCliente() {
        System.out.print("Ingrese el email del cliente: ");
        String email = scanner.nextLine();
        List<Pedido> pedidosEnviados = pedidoControlador.obtenerPedidosEnviadosPorCliente(email);

        if (pedidosEnviados.isEmpty()) {
            System.out.println("No hay pedidos enviados para este cliente.");
        } else {
            pedidosEnviados.forEach(System.out::println);
        }
    }

    /**
     * Solicita un número de pedido y lo elimina si es válido.
     */
    private void eliminarPedido() {
        System.out.print("Ingrese el número del pedido a eliminar: ");
        int numPedido = scanner.nextInt();
        scanner.nextLine();

        try {
            pedidoControlador.eliminarPedido(numPedido);
            System.out.println("Pedido eliminado con éxito.");
        } catch (ElementoNoEncontradoException | PedidoNoEliminableException e) {
            System.out.println(e.getMessage());
        }
    }
}
