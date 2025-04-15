package org.javinity.dao.interfaces;

import org.javinity.modelos.Cliente;

import java.util.List;

/**
 * Interfaz DAO para gestionar la persistencia de clientes en la base de datos.
 */
public interface ClienteDAO {
    void insertar(Cliente cliente);
    Cliente buscar(String email);
    List<Cliente> listar();
}
