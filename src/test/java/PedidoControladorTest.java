import org.javinity.controladores.PedidoControlador;
import org.javinity.dao.factory.DAOFactory;
import org.javinity.modelos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoControladorTest {

    private PedidoControlador pedidoControlador;
    private Cliente cliente;
    private Articulo articulo;

    @BeforeEach
    void setUp() {
        // Usamos DAOs reales con conexión a la BD
        pedidoControlador = new PedidoControlador(DAOFactory.getPedidoDAO());

        // Creamos un cliente y un artículo para usar en el pedido
        cliente = new ClienteEstandar("testcliente@email.com", "Test Cliente", "Calle Falsa 123", "00000000T");
        DAOFactory.getClienteDAO().insertar(cliente);

        articulo = new Articulo("TEST-A002", "Artículo Test", 99.99f, 5.0f, 5);
        DAOFactory.getArticuloDAO().insertar(articulo);
    }

    @Test
    void testContarPedidos() {
        int pedidosIniciales = pedidoControlador.contarPedidos();

        Pedido pedido = new Pedido(cliente, articulo, 2, LocalDateTime.now());
        pedidoControlador.agregarPedido(pedido);

        int pedidosActuales = pedidoControlador.contarPedidos();

        assertEquals(pedidosIniciales + 1, pedidosActuales,
                "El número de pedidos debería haber aumentado en 1.");
    }
}
