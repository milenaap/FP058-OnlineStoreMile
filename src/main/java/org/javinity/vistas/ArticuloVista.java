package org.javinity.vistas;

import org.javinity.controladores.ArticuloControlador;
import org.javinity.modelos.Articulo;

import java.util.Scanner;

/**
 * Clase que representa la vista del módulo de gestión de artículos.
 * Permite al usuario interactuar con el sistema a través de la consola,
 * mostrando menús y solicitando datos.
 * Forma parte de la arquitectura MVC, comunicándose exclusivamente con el controlador.
 *
 * @author Javinity
 */
public class ArticuloVista {
    private ArticuloControlador articuloControlador;
    private Scanner scanner;

    /**
     * Constructor de la clase ArticuloVista.
     *
     * @param articuloControlador Controlador de artículo que gestiona la lógica de negocio.
     */
    public ArticuloVista(ArticuloControlador articuloControlador) {
        this.articuloControlador = articuloControlador;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú de opciones relacionadas con la gestión de artículos
     * y permite al usuario navegar entre las distintas acciones disponibles.
     */
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n Gestión de Artículos");
            System.out.println("1. Añadir Artículo");
            System.out.println("2. Mostrar Artículos");
            System.out.println("3. Volver al menú principal");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> agregarArticulo();
                case 2 -> mostrarArticulos();
                case 3 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println(" Opción no válida.");
            }
        } while (opcion != 3);
    }

    /**
     * Solicita los datos del nuevo artículo al usuario por consola,
     * valida los datos introducidos y los envía al controlador para ser añadidos.
     */
    private void agregarArticulo() {
        try {
            System.out.print("Código del producto: ");
            String codigoProducto = scanner.nextLine();

            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine();

            System.out.print("Precio de venta: ");
            float precioVenta = Float.parseFloat(scanner.nextLine());
            if (precioVenta < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");

            System.out.print("Gastos de envío: ");
            float gastosEnvio = Float.parseFloat(scanner.nextLine());
            if (gastosEnvio < 0) throw new IllegalArgumentException("Los gastos de envío no pueden ser negativos.");

            System.out.print("Tiempo de preparación (minutos): ");
            int tiempoPrepEnvio = Integer.parseInt(scanner.nextLine());
            if (tiempoPrepEnvio < 0) throw new IllegalArgumentException("El tiempo de preparación no puede ser negativo.");

            articuloControlador.agregarArticulo(codigoProducto, new Articulo(codigoProducto, descripcion, precioVenta, gastosEnvio, tiempoPrepEnvio));
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes introducir un número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("Artículo agregado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Llama al controlador para mostrar la lista de artículos disponibles.
     */
    private void mostrarArticulos() {
        System.out.println("==========================================");
        System.out.println("Lista de Artículos:");
        System.out.println("==========================================");
        articuloControlador.mostrarArticulos();
    }
}
