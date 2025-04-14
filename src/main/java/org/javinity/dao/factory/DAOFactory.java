package org.javinity.dao.factory;

import org.javinity.dao.implementaciones.ArticuloDAOImpl;
import org.javinity.dao.interfaces.ArticuloDAO;

/**
 * Clase Factory que se encarga de instanciar los DAOs del sistema.
 * Permite desacoplar la lógica de negocio de la implementación concreta de acceso a datos.
 */
public class DAOFactory {

    /**
     * Devuelve una implementación concreta del DAO de Artículo.
     *
     * @return una instancia de ArticuloDAO
     */
    public static ArticuloDAO getArticuloDAO() {
        return new ArticuloDAOImpl();
    }

    // Aquí puedes añadir más métodos como estos para ClienteDAO y PedidoDAO cuando estén listos:
    // public static ClienteDAO getClienteDAO() { return new ClienteDAOImpl(); }
    // public static PedidoDAO getPedidoDAO() { return new PedidoDAOImpl(); }
}
