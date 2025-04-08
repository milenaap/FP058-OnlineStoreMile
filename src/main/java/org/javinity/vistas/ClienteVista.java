package org.javinity.vistas;

import org.javinity.controladores.ClienteControlador;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.ClienteEstandar;
import org.javinity.modelos.ClientePremium;

import java.util.Collection;
import java.util.Scanner;


/**
 * Clase que representa la vista de interacción con el usuario
 * para la gestión de clientes.
 * Permite añadir y mostrar clientes mediante una interfaz de consola.
 * Forma parte del patrón de diseño MVC y se comunica exclusivamente con el controlador.
 *
 * @author Javinity
 */
public class ClienteVista {
    private ClienteControlador clienteControlador;
    private Scanner scanner;

    /**
     * Constructor que inicializa la vista con su controlador asociado.
     *
     * @param clienteControlador Controlador que gestiona la lógica de clientes.
     */
    public ClienteVista(ClienteControlador clienteControlador) {
        this.clienteControlador = clienteControlador;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal para la gestión de clientes y
     * permite navegar entre opciones hasta que el usuario decida salir.
     */
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n Gestión de Clientes");
            System.out.println("1. Añadir Cliente");
            System.out.println("2. Mostrar Clientes");
            System.out.println("3. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> agregarCliente();
                case 2 -> mostrarClientes();
                case 3 -> System.out.println(" Volviendo al menú principal...");
                default -> System.out.println(" Opción no válida.");
            }
        } while (opcion != 3);
    }

    /**
     * Solicita los datos del cliente al usuario, determina si es estándar o premium
     * y delega la creación del cliente al controlador.
     */
    private void agregarCliente() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Domicilio: ");
            String domicilio = scanner.nextLine();

            System.out.print("NIF: ");
            String nif = scanner.nextLine();

            System.out.print("¿Es cliente Premium? (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            boolean esPremium = respuesta.equals("s");

            Cliente cliente = esPremium
                    ? new ClientePremium(email, nombre, domicilio, nif)
                    : new ClienteEstandar(email, nombre, domicilio, nif);

            clienteControlador.agregarCliente(email, cliente);

            // Mensaje de confirmación
            String tipo = esPremium ? "Cliente PREMIUM" : "Cliente ESTÁNDAR";
            System.out.println(tipo + " agregado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar el cliente. Intenta de nuevo.");
        }
    }

    /**
     * Llama al controlador para mostrar todos los clientes registrados.
     */
//    private void mostrarClientes() {
////        System.out.println("\n Lista de Clientes:");
////        clienteControlador.mostrarClientes();
////    }

    private void mostrarClientes() {
        System.out.println("==========================================");
        System.out.println(" Lista de Clientes:");
        System.out.println("==========================================");

        Collection<Cliente> listaClientes = clienteControlador.obtenerTodosLosClientes();

        if (listaClientes.isEmpty()) {
            System.out.println("📭 No hay clientes registrados.");
            return;
        }

        for (Cliente cliente : listaClientes) {
            System.out.println(cliente);
            System.out.println("==========================================");
        }
    }

}
