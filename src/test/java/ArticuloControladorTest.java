import org.javinity.controladores.ArticuloControlador;
import org.javinity.modelos.Articulo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticuloControladorTest {

    private ArticuloControlador articuloControlador;

//    @BeforeEach
//    void setUp() {
//        articuloControlador = new ArticuloControlador();
//    }
//
//    @Test
//    void testAgregarArticulo() {
//        Articulo articulo = new Articulo("A001", "Laptop HP", 800.0f, 20.0f, 30);
//        articuloControlador.agregarArticulo(articulo);
//
//        // Verificar que el artículo fue agregado correctamente
//        Articulo articuloObtenido = articuloControlador.obtenerArticulo("A001");
//        assertNotNull(articuloObtenido, "El artículo no debería ser nulo.");
//        assertEquals("Laptop HP", articuloObtenido.getDescripcion(), "La descripción del artículo no coincide.");
//        assertEquals(800.0f, articuloObtenido.getPrecioVenta(), "El precio del artículo no coincide.");
//    }
}
