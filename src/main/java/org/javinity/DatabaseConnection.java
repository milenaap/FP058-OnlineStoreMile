package org.javinity;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos MySQL.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/online_store_db";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    /**
     * Establece y devuelve una conexión con la base de datos.
     *
     * @return Objeto Connection
     * @throws SQLException si falla la conexión
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
