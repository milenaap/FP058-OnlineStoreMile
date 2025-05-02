import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.javinity.dao.ArticuloDAOImpl;
import org.javinity.modelos.Articulo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArticuloDAOTest {

    private EntityManager em;

    @BeforeEach
    void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineStorePU");
        em = emf.createEntityManager();
    }

    @Test
    @DisplayName("Insertar un artículo correctamente")
    void testInsertarArticulo() {
        ArticuloDAOImpl articuloDAO = new ArticuloDAOImpl(em);
        String codigo = "TEST-INSERT-" + (int) (Math.random() * 1000);
        float precioVenta = (float) (Math.random() * (500 - 10) + 10);

        Articulo articulo = new Articulo(codigo, "Producto de prueba", precioVenta, 5.0f, 10);
        articuloDAO.insertar(articulo);

        Articulo resultado = articuloDAO.buscar(codigo);
        assertNotNull(resultado);
        assertEquals(codigo, resultado.getCodigoProducto());
    }

    @Test
    @DisplayName("Buscar un artículo existente")
    void testBuscarArticulo() {
        ArticuloDAOImpl articuloDAO = new ArticuloDAOImpl(em);
        String codigo = "TEST-BUSCAR";

        Articulo existente = articuloDAO.buscar(codigo);
        if (existente == null) {
            Articulo articulo = new Articulo(codigo, "Buscar prueba", 100.0f, 5.0f, 20);
            articuloDAO.insertar(articulo);
        }

        Articulo resultado = articuloDAO.buscar(codigo);
        assertNotNull(resultado);
        assertEquals(codigo, resultado.getCodigoProducto());
    }

    @Test
    @DisplayName("Listar todos los artículos")
    void testListarArticulos() {
        ArticuloDAOImpl articuloDAO = new ArticuloDAOImpl(em);
        String codigo = "TEST-LISTAR";

        if (articuloDAO.buscar(codigo) == null) {
            Articulo articulo = new Articulo(codigo, "Producto listar", 150.0f, 8.0f, 25);
            articuloDAO.insertar(articulo);
        }

        List<Articulo> lista = articuloDAO.listar();
        assertNotNull(lista);
        assertTrue(lista.size() > 0);
        assertTrue(lista.stream().anyMatch(a -> a.getCodigoProducto().equals(codigo)));
    }

    @Test
    @DisplayName("Eliminar un artículo existente")
    void testEliminarArticulo() {
        ArticuloDAOImpl articuloDAO = new ArticuloDAOImpl(em);
        String codigo = "TEST-ELIMINAR";

        Articulo articulo = articuloDAO.buscar(codigo);
        if (articulo == null) {
            articulo = new Articulo(codigo, "Producto eliminar", 200.0f, 6.0f, 30);
            articuloDAO.insertar(articulo);
        }

        articuloDAO.eliminar(codigo);
        Articulo eliminado = articuloDAO.buscar(codigo);
        assertNull(eliminado, "El artículo no fue eliminado correctamente");
    }
}

