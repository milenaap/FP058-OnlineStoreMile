package org.javinity.modelos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa un cliente general.
 * Es la clase padre para ClienteEstandar y ClientePremium.
 *
 * @author Javinity
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "clientes")
public abstract class Cliente {

    @Id
    @Column(name = "email")
    private String email;

    private String nombre;
    private String domicilio;
    private String nif;
    // Mapeo de pedidos
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Cliente(){}

    public Cliente(String email, String nombre, String domicilio, String nif) {
        this.email = email;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;

    }

    public Cliente(String email) {
        this.email = email;
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
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
