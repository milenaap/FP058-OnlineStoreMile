package org.javinity.dao.implementaciones;

import org.javinity.DatabaseConnection;
import org.javinity.dao.interfaces.ClienteDAO;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.ClienteEstandar;
import org.javinity.modelos.ClientePremium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del DAO de clientes usando JDBC para la gestión con MySQL.
 */
public class ClienteDAOImpl implements ClienteDAO {

    /**
     * Inserta un cliente en la base de datos.
     *
     * @param cliente El cliente a insertar.
     */
    @Override
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (email, nombre, domicilio, nif, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getEmail());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getDomicilio());
            stmt.setString(4, cliente.getNif());
            stmt.setString(5, cliente instanceof ClientePremium ? "premium" : "estandar");

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca un cliente por su email.
     *
     * @param email Email del cliente.
     * @return Objeto Cliente si se encuentra, null si no existe.
     */
    @Override
    public Cliente buscar(String email) {
        String sql = "SELECT * FROM clientes WHERE email = ?";
        Cliente cliente = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String domicilio = rs.getString("domicilio");
                String nif = rs.getString("nif");
                String tipo = rs.getString("tipo");

                cliente = construirCliente(email, nombre, domicilio, nif, tipo);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return cliente;
    }

    /**
     * Lista todos los clientes registrados.
     *
     * @return Lista de clientes.
     */
    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String email = rs.getString("email");
                String nombre = rs.getString("nombre");
                String domicilio = rs.getString("domicilio");
                String nif = rs.getString("nif");
                String tipo = rs.getString("tipo");

                Cliente cliente = construirCliente(email, nombre, domicilio, nif, tipo);
                clientes.add(cliente);
            }

        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    /**
     * Crea un objeto Cliente concreto a partir del tipo.
     *
     * @param email     Email del cliente.
     * @param nombre    Nombre del cliente.
     * @param domicilio Domicilio del cliente.
     * @param nif       NIF del cliente.
     * @param tipo      Tipo de cliente ("premium" o "estandar").
     * @return Instancia de ClientePremium o ClienteEstandar.
     */
    private Cliente construirCliente(String email, String nombre, String domicilio, String nif, String tipo) {
        return "premium".equalsIgnoreCase(tipo)
                ? new ClientePremium(email, nombre, domicilio, nif)
                : new ClienteEstandar(email, nombre, domicilio, nif);
    }
}
