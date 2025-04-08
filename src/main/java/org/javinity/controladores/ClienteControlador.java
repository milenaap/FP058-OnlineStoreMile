package org.javinity.controladores;


import org.javinity.RepositorioGenerico;
import org.javinity.modelos.Cliente;

import java.util.Collection;

/**
 * Controlador encargado de gestionar la lógica de negocio relacionada con los clientes.
 * Funciona como intermediario entre la vista y el repositorio de datos.
 * Implementa operaciones CRUD básicas sobre clientes.
 *
 * @author Javinity
 */
public class ClienteControlador {
    private RepositorioGenerico<Cliente> clientes;


    /**
     * Constructor que inicializa el repositorio de clientes.
     */
    public ClienteControlador() {
        this.clientes = new RepositorioGenerico<>();
    }



    /**
     * Agrega un nuevo cliente si no existe otro con el mismo email.
     *
     * @param email   Email del cliente (clave única).
     * @param cliente Objeto cliente a agregar.
     * @throws IllegalArgumentException si ya existe un cliente con ese email.
     */
    public void agregarCliente(String email, Cliente cliente) {
        if (clientes.obtener(email) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + email);
        }
        clientes.agregar(email, cliente);
    }


    /**
     * Devuelve el cliente correspondiente al email proporcionado.
     *
     * @param email Email del cliente.
     * @return Cliente correspondiente, o null si no existe.
     */
    public Cliente obtenerCliente(String email) {
        return clientes.obtener(email);
    }


    /**
     * Elimina un cliente dado su email.
     *
     * @param email Email del cliente a eliminar.
     */
    public void eliminarCliente(String email) {
        clientes.eliminar(email);
    }


    /**
     * Muestra todos los clientes registrados.
     */
//    public void mostrarClientes() {
//        clientes.mostrarTodos();
//    }

    /**
     * Devuelve una colección con todos los clientes registrados.
     *
     * @return Lista de clientes.
     */
    public Collection<Cliente> obtenerTodosLosClientes() {
        return clientes.obtenerTodos().values();
    }

}
