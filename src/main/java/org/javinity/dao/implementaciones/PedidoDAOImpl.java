package org.javinity.dao.implementaciones;

import org.javinity.DatabaseConnection;
import org.javinity.dao.interfaces.ArticuloDAO;
import org.javinity.dao.interfaces.ClienteDAO;
import org.javinity.dao.interfaces.PedidoDAO;
import org.javinity.modelos.Articulo;
import org.javinity.modelos.Cliente;
import org.javinity.modelos.Pedido;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del DAO de pedidos utilizando JDBC.
 * Gestiona la persistencia de pedidos en la base de datos MySQL.
 */
public class PedidoDAOImpl implements PedidoDAO {

    private final ClienteDAO clienteDAO;
    private final ArticuloDAO articuloDAO;

    /**
     * Constructor que recibe DAOs auxiliares necesarios para reconstruir relaciones.
     *
     * @param clienteDAO  DAO de clientes.
     * @param articuloDAO DAO de artículos.
     */
    public PedidoDAOImpl(ClienteDAO clienteDAO, ArticuloDAO articuloDAO) {
        this.clienteDAO = clienteDAO;
        this.articuloDAO = articuloDAO;
    }

    /**
     * Inserta un nuevo pedido en la base de datos.
     *
     * @param pedido El pedido a insertar.
     */
    @Override
    public void insertar(Pedido pedido) {
        String sql = "{CALL sp_insertar_pedido(?, ?, ?, ?)}";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, pedido.getCliente().getEmail());
            stmt.setString(2, pedido.getArticulo().getCodigoProducto());
            stmt.setInt(3, pedido.getCantidad());
            stmt.setTimestamp(4, Timestamp.valueOf(pedido.getFechaHoraPedido()));

            stmt.execute();

            // Opcional: puedes hacer una consulta posterior para obtener el último ID insertado
            // si necesitas asignar numPedido al objeto Pedido
            System.out.println("Pedido insertado correctamente usando procedimiento almacenado.");

        } catch (SQLException e) {
            System.out.println("Error al insertar pedido (procedimiento): " + e.getMessage());
        }
    }

    /**
     * Lista todos los pedidos almacenados en la base de datos.
     *
     * @return Lista completa de pedidos.
     */
    @Override
    public List<Pedido> listar() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int numPedido = rs.getInt("num_pedido");
                String email = rs.getString("email_cliente");
                String codigoProducto = rs.getString("codigo_producto");
                int cantidad = rs.getInt("cantidad");
                LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();

                Cliente cliente = clienteDAO.buscar(email);
                Articulo articulo = articuloDAO.buscar(codigoProducto);

                Pedido pedido = new Pedido(numPedido, cliente, articulo, cantidad, fechaHora);
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }

    /**
     * Busca un pedido en base a su número identificador.
     *
     * @param numPedido Número del pedido.
     * @return Pedido si se encuentra, null si no existe.
     */
    @Override
    public Pedido buscar(int numPedido) {
        String sql = "SELECT * FROM pedidos WHERE num_pedido = ?";
        Pedido pedido = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email_cliente");
                    String codigoProducto = rs.getString("codigo_producto");
                    int cantidad = rs.getInt("cantidad");
                    LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();

                    Cliente cliente = clienteDAO.buscar(email);
                    Articulo articulo = articuloDAO.buscar(codigoProducto);

                    pedido = new Pedido(numPedido, cliente, articulo, cantidad, fechaHora);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar pedido: " + e.getMessage());
        }

        return pedido;
    }

    /**
     * Elimina un pedido de la base de datos.
     *
     * @param numPedido Número del pedido a eliminar.
     */
    @Override
    public void eliminar(int numPedido) {
        String sql = "DELETE FROM pedidos WHERE num_pedido = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numPedido);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }
}
