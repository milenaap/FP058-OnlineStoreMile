import org.javinity.controladores.ArticuloControlador;
import org.javinity.dao.factory.DAOFactory;
import org.javinity.modelos.Articulo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArticuloControladorTest {

    private ArticuloControlador articuloControlador;

    @BeforeEach
    void setUp() {
        articuloControlador = new ArticuloControlador(DAOFactory.getArticuloDAO());
    }

    @Test
    void testAgregarArticulo() {
        Articulo articulo = new Articulo("TEST-001", "Test producto", 99.99f, 10.0f, 15);
        articuloControlador.agregarArticulo(articulo);

        Articulo articuloObtenido = articuloControlador.obtenerArticulo("TEST-001");

        assertNotNull(articuloObtenido);
        assertEquals("Test producto", articuloObtenido.getDescripcion());
        assertEquals(99.99f, articuloObtenido.getPrecioVenta());
    }
}

