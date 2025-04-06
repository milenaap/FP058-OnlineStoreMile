package org.javinity;

import org.javinity.controladores.ArticuloControlador;
import org.javinity.controladores.ClienteControlador;
import org.javinity.controladores.PedidoControlador;
import org.javinity.vistas.ArticuloVista;
import org.javinity.vistas.ClienteVista;
import org.javinity.vistas.PedidoVista;
import org.javinity.vistas.MenuPrincipal;

/**
 * Clase principal del proyecto Online Store.
 * Encargada de lanzar la aplicación desde consola.
 */
public class Main {
    public static void main(String[] args) {
        // Instanciar controladores
        ArticuloControlador articuloControlador = new ArticuloControlador();
        ClienteControlador clienteControlador = new ClienteControlador();
        PedidoControlador pedidoControlador = new PedidoControlador();

        // Instanciar vistas
        ArticuloVista articuloVista = new ArticuloVista(articuloControlador);
        ClienteVista clienteVista = new ClienteVista(clienteControlador);
        PedidoVista pedidoVista = new PedidoVista(pedidoControlador, clienteControlador, articuloControlador);

        // Lanzar menú principal
        MenuPrincipal menu = new MenuPrincipal(articuloVista, clienteVista, pedidoVista);
        menu.mostrar();
    }
}