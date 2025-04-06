package org.javinity.vistas;

import java.util.Scanner;

/**
 * Vista general que representa el menú principal del sistema.
 * Permite navegar entre las vistas de Artículos, Clientes y Pedidos.
 */
public class MenuPrincipal {

    private final ArticuloVista articuloVista;
    private final ClienteVista clienteVista;
    private final PedidoVista pedidoVista;
    private final Scanner scanner;

    /**
     * Constructor que recibe las vistas necesarias para el sistema.
     *
     * @param articuloVista Vista de artículos
     * @param clienteVista  Vista de clientes
     * @param pedidoVista   Vista de pedidos
     */
    public MenuPrincipal(ArticuloVista articuloVista, ClienteVista clienteVista, PedidoVista pedidoVista) {
        this.articuloVista = articuloVista;
        this.clienteVista = clienteVista;
        this.pedidoVista = pedidoVista;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal y permite seleccionar cada módulo del sistema.
     */
    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n============================================");
            System.out.println("|         ONLINE STORE - MENÚ PRINCIPAL     |");
            System.out.println("============================================");
            System.out.println("| 1. Gestión de Artículos                   |");
            System.out.println("| 2. Gestión de Clientes                    |");
            System.out.println("| 3. Gestión de Pedidos                     |");
            System.out.println("| 4. Salir                                  |");
            System.out.println("============================================");
            System.out.print("Selecciona una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Ingresa un número.");
                scanner.next(); // descarta entrada incorrecta
            }

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpia el buffer

            switch (opcion) {
                case 1 -> articuloVista.mostrarMenu();
                case 2 -> clienteVista.mostrarMenu();
                case 3 -> pedidoVista.mostrarMenu();
                case 4 -> System.out.println("Cerrando aplicación. ¡Hasta luego!");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 4);
    }
}

