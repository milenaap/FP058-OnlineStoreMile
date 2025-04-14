package org.javinity.vistas;

import org.javinity.controladores.ArticuloControlador;
import org.javinity.modelos.Articulo;

import java.util.List;
import java.util.Scanner;

/**
 * Vista encargada de gestionar la interacción con el usuario para los artículos.
 * Forma parte de la capa de presentación en el patrón MVC.
 * Permite añadir y mostrar artículos usando el controlador.
 */
public class ArticuloVista {

    private ArticuloControlador articuloControlador;
    private Scanner scanner;

    /**
     * Constructor de la vista.
     *
     * @param articuloControlador Controlador de artículos que maneja la lógica de negocio.
     */
    public ArticuloVista(ArticuloControlador articuloControlador) {
        this.articuloControlador = articuloControlador;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú de gestión de artículos en consola.
     * Permite al usuario seleccionar distintas operaciones: agregar, mostrar o salir.
     */
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== Gestión de Artículos ===");
            System.out.println("1. Añadir artículo");
            System.out.println("2. Mostrar artículos");
            System.out.println("3. Salir al menú principal");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> agregarArticulo();
                    case 2 -> mostrarArticulos();
                    case 3 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un número válido.");
                opcion = -1;
            }

        } while (opcion != 3);
    }

    /**
     * Solicita los datos del artículo al usuario y delega su almacenamiento al controlador.
     * Realiza validaciones básicas e informa al usuario del resultado.
     */
    private void agregarArticulo() {
        try {
            System.out.print("Código del producto: ");
            String codigo = scanner.nextLine();

            System.out.print("Descripción: ");
            String descripcion = scanner.nextLine();

            System.out.print("Precio de venta: ");
            float precioVenta = Float.parseFloat(scanner.nextLine());

            System.out.print("Gastos de envío: ");
            float gastosEnvio = Float.parseFloat(scanner.nextLine());

            System.out.print("Tiempo de preparación (minutos): ");
            int tiempo = Integer.parseInt(scanner.nextLine());

            Articulo nuevo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempo);
            articuloControlador.agregarArticulo(nuevo);

            System.out.println("Artículo agregado correctamente.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    /**
     * Muestra en consola todos los artículos existentes en la base de datos.
     * Si no hay artículos registrados, muestra un mensaje informativo.
     */
    private void mostrarArticulos() {
        List<Articulo> articulos = articuloControlador.obtenerTodos();

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
        } else {
            System.out.println("\nLista de artículos:");
            for (Articulo articulo : articulos) {
                System.out.println(articulo);
            }
        }
    }
}