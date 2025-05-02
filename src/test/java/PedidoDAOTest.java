import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.javinity.dao.ArticuloDAOImpl;
import org.javinity.dao.ClienteDAOImpl;
import org.javinity.dao.PedidoDAOImpl;
import org.javinity.modelos.Articulo;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.ClienteEstandar;
import org.javinity.modelos.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoDAOTest {
    private EntityManager em;
    private PedidoDAOImpl pedidoDAO;
    private ClienteDAOImpl clienteDAO;
    private ArticuloDAOImpl articuloDAO;

    @BeforeEach
    void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineStorePU");
        em = emf.createEntityManager();

        clienteDAO = new ClienteDAOImpl(em);
        articuloDAO = new ArticuloDAOImpl(em);
        pedidoDAO = new PedidoDAOImpl(em);
    }

    @Test
    void testCRUDPedido() {
        // Preparar Cliente y Articulo
        String email = "pedido@test.com";
        String codigo = "PED-ART-001";

        clienteDAO.eliminar(email);
        articuloDAO.eliminar(codigo);

        Cliente cliente = new ClienteEstandar(email, "Cliente Pedido", "Calle Test", "00000000P");
        Articulo articulo = new Articulo(codigo, "Artículo Pedido", 120.0f, 9.0f, 2);

        clienteDAO.insertar(cliente);
        articuloDAO.insertar(articulo);

        // INSERTAR Pedido
        Pedido pedido = new Pedido(cliente, articulo, 3, LocalDateTime.now());
        pedidoDAO.insertar(pedido);
        assertTrue(pedido.getNumPedido() > 0); // Hibernate le asigna ID

        // BUSCAR Pedido
        Pedido buscado = pedidoDAO.buscar(pedido.getNumPedido());
        assertNotNull(buscado);
        assertEquals(pedido.getCliente().getEmail(), buscado.getCliente().getEmail());

        // LISTAR Pedidos
        List<Pedido> pedidos = pedidoDAO.listar();
        assertNotNull(pedidos);
        assertTrue(pedidos.size() > 0);
        System.out.println("Pedidos listados:");
        pedidos.forEach(System.out::println);

        // ELIMINAR Pedido
        pedidoDAO.eliminar(pedido.getNumPedido());
        Pedido eliminado = pedidoDAO.buscar(pedido.getNumPedido());
        assertNull(eliminado);
    }

}
