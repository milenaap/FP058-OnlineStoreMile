import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.javinity.dao.ClienteDAOImpl;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.ClienteEstandar;
import org.javinity.modelos.ClientePremium;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDAOTest {
    private EntityManager em;
    private ClienteDAOImpl clienteDAO;

    @BeforeEach
    void setUp(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineStorePU");
        em = emf.createEntityManager();
        clienteDAO = new ClienteDAOImpl(em);
    }

    @Test
    void testInsertarYBuscarClientesPremium(){
        String email = "premium@test.com";
        clienteDAO.eliminar(email);

        ClientePremium cliente = new ClientePremium(email, "Ana Premium", "Calle Oro", "12345678P");
        clienteDAO.insertar(cliente);

        Cliente buscado = clienteDAO.buscar(email);
        assertNotNull(buscado);
        assertEquals(email, buscado.getEmail());
        System.out.println("Cliente Premium insertado y recuperado: " + buscado);
    }

    @Test
    void testInsertarYBuscarClienteEstandar() {
        String email = "estandar@test.com";
        clienteDAO.eliminar(email);

        ClienteEstandar cliente = new ClienteEstandar(email, "Luis Estandar", "Calle Plata", "87654321E");
        clienteDAO.insertar(cliente);

        Cliente buscado = clienteDAO.buscar(email);
        assertNotNull(buscado);
        assertEquals(email, buscado.getEmail());
        System.out.println("Cliente Estandar insertado y recuperado: " + buscado);
    }

    @Test
    void testListarClientes() {
        List<Cliente> clientes = clienteDAO.listar();
        assertNotNull(clientes);
        System.out.println("Listado de clientes:");
        for (Cliente c : clientes) {
            System.out.println(" - " + c);
        }
    }

    @Test
    void testEliminarCliente() {
        String email = "eliminar@test.com";
        ClientePremium cliente = new ClientePremium(email, "Eliminar", "Calle Borrada", "99999999X");
        clienteDAO.insertar(cliente);

        assertNotNull(clienteDAO.buscar(email));

        clienteDAO.eliminar(email);

        assertNull(clienteDAO.buscar(email));
        System.out.println("Cliente eliminado correctamente.");
    }

}
