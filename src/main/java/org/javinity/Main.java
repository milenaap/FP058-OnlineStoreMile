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
import org.javinity.vistas.MenuPrincipal;
import org.javinity.vistas.PedidoVista;

public class Main {
    public static void main(String[] args) {

        // Crear EntityManagerFactory con el nombre definido en persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineStorePU");
        EntityManager em = emf.createEntityManager();

        // Obtener DAO
        ArticuloDAOImpl articuloDAO = new ArticuloDAOImpl(em);
        ClienteDAOImpl clienteDAO = new ClienteDAOImpl(em);
        PedidoDAOImpl pedidoDAO = new PedidoDAOImpl(em);

        // Controladores
        ArticuloControlador articuloControlador = new ArticuloControlador(articuloDAO);
        ClienteControlador clienteControlador = new ClienteControlador(clienteDAO);
        PedidoControlador pedidoControlador = new PedidoControlador(pedidoDAO);

        // Vistas con controladores
        MenuPrincipal.articuloVista = new ArticuloVista(articuloControlador);
        MenuPrincipal.clienteVista = new ClienteVista(clienteControlador);
        MenuPrincipal.pedidoVista = new PedidoVista(pedidoControlador, clienteControlador, articuloControlador);

        // Lanzar JavaFX
        MenuPrincipal.main(args);
    }
}
