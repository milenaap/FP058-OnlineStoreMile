package org.javinity.dao.implementaciones;

import org.javinity.DatabaseConnection;
import org.javinity.dao.interfaces.ArticuloDAO;
import org.javinity.modelos.Articulo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de artículo usando JDBC para interactuar con MySQL.
 * Gestiona la inserción, búsqueda, listado y eliminación de artículos.
 */
public class ArticuloDAOImpl implements ArticuloDAO {

    /**
     * Inserta un nuevo artículo en la base de datos.
     *
     * @param articulo Artículo a insertar.
     */
    @Override
    public void insertar(Articulo articulo) {
        String sql = "INSERT INTO articulos (codigo_producto, descripcion, precio_venta, gastos_envio, tiempo_preparacion_envio) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, articulo.getCodigoProducto());
            stmt.setString(2, articulo.getDescripcion());
            stmt.setFloat(3, articulo.getPrecioVenta());
            stmt.setFloat(4, articulo.getGastosEnvio());
            stmt.setInt(5, articulo.getTiempoPrepEnvio());

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al insertar el artículo: " + e.getMessage());
        }
    }

    /**
     * Busca un artículo en la base de datos por su código.
     *
     * @param codigo Código del artículo.
     * @return Objeto Articulo si existe, null si no se encuentra.
     */
    @Override
    public Articulo buscar(String codigo) {
        String sql = "SELECT * FROM articulos WHERE codigo_producto = ?";
        Articulo articulo = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String descripcion = rs.getString("descripcion");
                float precioVenta = rs.getFloat("precio_venta");
                float gastosEnvio = rs.getFloat("gastos_envio");
                int tiempo = rs.getInt("tiempo_preparacion_envio");

                articulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempo);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar el artículo: " + e.getMessage());
        }

        return articulo;
    }

    /**
     * Obtiene todos los artículos almacenados en la base de datos.
     *
     * @return Lista de artículos.
     */
    @Override
    public List<Articulo> listar() {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulos";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("codigo_producto");
                String descripcion = rs.getString("descripcion");
                float precioVenta = rs.getFloat("precio_venta");
                float gastosEnvio = rs.getFloat("gastos_envio");
                int tiempo = rs.getInt("tiempo_preparacion_envio");

                Articulo articulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempo);
                articulos.add(articulo);
            }

        } catch (Exception e) {
            System.out.println("Error al listar los artículos: " + e.getMessage());
        }

        return articulos;
    }

    /**
     * Elimina un artículo de la base de datos por su código.
     *
     * @param codigo Código del artículo a eliminar.
     */
    @Override
    public void eliminar(String codigo) {
        String sql = "DELETE FROM articulos WHERE codigo_producto = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, codigo);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Artículo eliminado correctamente.");
            } else {
                System.out.println("No se encontró un artículo con ese código.");
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el artículo: " + e.getMessage());
        }
    }
}
