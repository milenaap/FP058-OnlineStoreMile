package org.javinity.controladores;

import org.javinity.dao.interfaces.ClienteDAO;
import org.javinity.excepciones.ElementoNoEncontradoException;
import org.javinity.modelos.Cliente;

import java.util.Collection;

/**
 * Controlador encargado de gestionar la lógica de negocio relacionada con los clientes.
 * Funciona como intermediario entre la vista y el acceso a base de datos mediante DAO.
 */
public class ClienteControlador {
    private final ClienteDAO clienteDAO;

    /**
     * Constructor que recibe el DAO para acceder a los datos de clientes.
     *
     * @param clienteDAO DAO concreto que gestiona la persistencia.
     */
    public ClienteControlador(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    /**
     * Agrega un nuevo cliente si no existe otro con el mismo email.
     *
     * @param cliente Objeto cliente a agregar.
     * @throws IllegalArgumentException si ya existe un cliente con ese email.
     */
    public void agregarCliente(Cliente cliente) {
        if (clienteDAO.buscar(cliente.getEmail()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + cliente.getEmail());
        }
        clienteDAO.insertar(cliente);
    }

    /**
     * Devuelve el cliente correspondiente al email proporcionado.
     *
     * @param email Email del cliente.
     * @return Cliente correspondiente.
     * @throws ElementoNoEncontradoException si no se encuentra el cliente.
     */
    public Cliente obtenerCliente(String email) {
        Cliente cliente = clienteDAO.buscar(email);
        if (cliente == null) {
            throw new ElementoNoEncontradoException("Cliente con email " + email + " no encontrado.");
        }
        return cliente;
    }

    /**
     * Devuelve una colección con todos los clientes registrados.
     *
     * @return Lista de clientes.
     */
    public Collection<Cliente> obtenerTodosLosClientes() {
        return clienteDAO.listar();
    }
}
