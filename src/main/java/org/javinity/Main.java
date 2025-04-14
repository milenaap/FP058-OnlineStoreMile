package org.javinity;

import org.javinity.controladores.ArticuloControlador;
import org.javinity.controladores.ClienteControlador;
import org.javinity.controladores.PedidoControlador;
import org.javinity.dao.factory.DAOFactory;
import org.javinity.dao.interfaces.ArticuloDAO;
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
        // Obtener el DAO usando el patrón Factory
        ArticuloDAO articuloDAO = DAOFactory.getArticuloDAO();

        // Inyectar DAO al controlador
        ArticuloControlador articuloControlador = new ArticuloControlador(articuloDAO);
        ClienteControlador clienteControlador = new ClienteControlador();
        PedidoControlador pedidoControlador = new PedidoControlador();

        // Instanciar vistas con sus respectivos controladores
        ArticuloVista articuloVista = new ArticuloVista(articuloControlador);
        ClienteVista clienteVista = new ClienteVista(clienteControlador);
        PedidoVista pedidoVista = new PedidoVista(pedidoControlador, clienteControlador, articuloControlador);

        // Lanzar menú principal
        MenuPrincipal menu = new MenuPrincipal(articuloVista, clienteVista, pedidoVista);
        menu.mostrar();
    }
}
