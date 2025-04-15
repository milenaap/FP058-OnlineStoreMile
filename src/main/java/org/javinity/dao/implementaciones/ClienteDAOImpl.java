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
 * Implementación de ClienteDAO usando JDBC para conexión con MySQL.
 */
public class ClienteDAOImpl implements ClienteDAO {

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

                if ("premium".equalsIgnoreCase(tipo)) {
                    cliente = new ClientePremium(email, nombre, domicilio, nif);
                } else {
                    cliente = new ClienteEstandar(email, nombre, domicilio, nif);
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return cliente;
    }

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

                Cliente cliente = "premium".equalsIgnoreCase(tipo)
                        ? new ClientePremium(email, nombre, domicilio, nif)
                        : new ClienteEstandar(email, nombre, domicilio, nif);

                clientes.add(cliente);
            }

        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

}
