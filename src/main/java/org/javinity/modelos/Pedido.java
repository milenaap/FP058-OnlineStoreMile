package org.javinity.modelos;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa un pedido realizado en la tienda online.
 * Contiene la información del cliente, artículo solicitado, cantidad, fecha y número de pedido.
 *
 * Se utiliza tanto para registrar nuevos pedidos como para recuperar pedidos desde la base de datos.
 *
 * Forma parte del modelo del proyecto (patrón MVC).
 *
 * @author Javinity
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numPedido; // Identificador único del pedido (PK)

    @ManyToOne
    @JoinColumn(name = "email_cliente", referencedColumnName = "email", nullable = false)
    private Cliente cliente; // Cliente que realiza el pedido

    @ManyToOne
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto", nullable = false)
    private Articulo articulo; // Artículo solicitado

    @Column(nullable = false)
    private int cantidad; // Número de unidades pedidas

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHoraPedido; // Fecha y hora en que se realiza el pedido




    // Constructor vacío requerido por JPA
    public Pedido(){}

    /**
     * Constructor para insertar un nuevo pedido (sin número aún asignado).
     *
     * @param cliente Cliente que realiza el pedido
     * @param articulo Artículo solicitado
     * @param cantidad Cantidad solicitada
     * @param fechaHoraPedido Fecha y hora del pedido
     */
    public Pedido(Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHoraPedido) {
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHoraPedido = fechaHoraPedido;
    }

    /**
     * Constructor para recuperar un pedido desde la base de datos (ya tiene numPedido asignado).
     *
     * @param numPedido Número del pedido (clave primaria)
     * @param cliente Cliente que realizó el pedido
     * @param articulo Artículo solicitado
     * @param cantidad Cantidad pedida
     * @param fechaHoraPedido Fecha y hora del pedido
     */
    public Pedido(int numPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHoraPedido) {
        this.numPedido = numPedido;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHoraPedido = fechaHoraPedido;
    }

    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaHoraPedido() {
        return fechaHoraPedido;
    }

    public void setFechaHoraPedido(LocalDateTime fechaHoraPedido) {
        this.fechaHoraPedido = fechaHoraPedido;
    }

    /**
     * Devuelve una representación legible del pedido con sus datos principales.
     *
     * @return Texto con los datos del pedido.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Pedido #" + numPedido +
                " | Cliente: " + cliente.getNombre() +
                " | Artículo: " + articulo.getDescripcion() +
                " | Cantidad: " + cantidad +
                " | Fecha: " + fechaHoraPedido.format(formatter);
    }
}
