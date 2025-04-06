package org.javinity.modelos;

import java.util.ArrayList;

/**
 * Clase abstracta que representa un cliente general de la tienda online.
 * Incluye los datos comunes a todos los tipos de cliente.
 * Contiene una lista de pedidos realizados por el cliente.
 *
 * @author Javinity
 */
public abstract class Cliente {

    private String email;
    private String nombre;
    private String domicilio;
    private String nif;
    private ArrayList<Pedido> pedidos;

    public Cliente(String email, String nombre, String domicilio, String nif) {
        this.email = email;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.pedidos = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", nif='" + nif + '\'' +
                '}';
    }
}
