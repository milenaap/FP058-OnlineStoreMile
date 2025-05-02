package org.javinity.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Representa a un cliente de tipo estándar.
 * No tiene beneficios adicionales.
 */
@Entity
@Table(name = "clientes_estandar")
public class ClienteEstandar extends Cliente {

    // Constructor vacío requerido por JPA
    public ClienteEstandar(){}

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
