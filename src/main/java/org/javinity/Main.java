package org.javinity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.javinity.controladores.ArticuloControlador;
import org.javinity.controladores.ClienteControlador;
import org.javinity.controladores.PedidoControlador;
import org.javinity.dao.ArticuloDAOImpl;
import org.javinity.dao.ClienteDAOImpl;
import org.javinity.dao.PedidoDAOImpl;
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

        // Crear EntityManagerFactory con el nombre definido en persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineStorePU");
        EntityManager em = emf.createEntityManager();

        // Obtener el DAO usando JPA
        ArticuloDAOImpl articuloDAO = new ArticuloDAOImpl(em);
        ClienteDAOImpl clienteDAO = new ClienteDAOImpl(em);
        PedidoDAOImpl pedidoDAO = new PedidoDAOImpl(em);


        // Inyectar DAO al controlador
        ArticuloControlador articuloControlador = new ArticuloControlador(articuloDAO);
        ClienteControlador clienteControlador = new ClienteControlador(clienteDAO);
        PedidoControlador pedidoControlador = new PedidoControlador(pedidoDAO);

        // Instanciar vistas con sus respectivos controladores
        ArticuloVista articuloVista = new ArticuloVista(articuloControlador);
        ClienteVista clienteVista = new ClienteVista(clienteControlador);
        PedidoVista pedidoVista = new PedidoVista(pedidoControlador, clienteControlador, articuloControlador);

        // Lanzar menú principal
        MenuPrincipal menu = new MenuPrincipal(articuloVista, clienteVista, pedidoVista);
        menu.mostrar();
    }
}
