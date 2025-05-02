package org.javinity.modelos;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Representa a un cliente de tipo premium.
 * Tiene cuota fija y descuento en gastos de envío.
 */
@Entity
@Table(name = "clientes_premium")
public class ClientePremium extends Cliente{

    public static final double CUOTA = 30;
    public static final int DESCUENTO = 20;

    // Constructor vacío requerido por JPA
    public ClientePremium(){

    }
    public ClientePremium(String email, String nombre, String domicilio, String nif) {
        super(email, nombre, domicilio, nif);
    }


    @Override
    public String toString() {
        return "Cliente PREMIUM" +
                "\nNombre: " + getNombre() +
                "\nNIF: " + getNif() +
                "\nEmail: " + getEmail() +
                "\nDomicilio: " + getDomicilio() +
                "\nCuota: " + ClientePremium.CUOTA + "€" +
                "\nDescuento: " + ClientePremium.DESCUENTO + "%";
    }
}
