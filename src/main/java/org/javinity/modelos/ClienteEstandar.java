package org.javinity.modelos;

/**
 * Representa a un cliente de tipo estándar.
 * No tiene beneficios adicionales.
 */
public class ClienteEstandar extends Cliente {


    public ClienteEstandar(String email, String nombre, String domicilio, String nif) {
        super(email, nombre, domicilio, nif);
    }

    @Override
    public String toString() {
        return "Cliente ESTANDAR" +
                "\nNombre: " + getNombre() +
                "\nNIF: " + getNif() +
                "\nEmail: " + getEmail() +
                "\nDomicilio: " + getDomicilio();
    }
}
