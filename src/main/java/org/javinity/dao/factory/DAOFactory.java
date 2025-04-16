package org.javinity.dao.factory;

import org.javinity.dao.implementaciones.ArticuloDAOImpl;
import org.javinity.dao.implementaciones.ClienteDAOImpl;
import org.javinity.dao.implementaciones.PedidoDAOImpl;
import org.javinity.dao.interfaces.ArticuloDAO;
import org.javinity.dao.interfaces.ClienteDAO;
import org.javinity.dao.interfaces.PedidoDAO;

/**
 * Fábrica de DAOs: proporciona instancias de acceso a datos desacopladas.
 */
public class DAOFactory {

    /**
     * Devuelve una implementación concreta del DAO de artículos.
     *
     * @return ArticuloDAO
     */
    public static ArticuloDAO getArticuloDAO() {
        return new ArticuloDAOImpl();
    }

    /**
     * Devuelve una implementación concreta del DAO de clientes.
     *
     * @return ClienteDAO
     */
    public static ClienteDAO getClienteDAO() {
        return new ClienteDAOImpl();
    }

    /**
     * Devuelve una implementación concreta del DAO de pedidos.
     * Requiere instancias de ClienteDAO y ArticuloDAO como dependencias.
     *
     * @return PedidoDAO
     */
    public static PedidoDAO getPedidoDAO() {
        return new PedidoDAOImpl(getClienteDAO(), getArticuloDAO());
    }
}
