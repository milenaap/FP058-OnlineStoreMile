package org.javinity.modelos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {

    private int numPedido; // Identificador del pedido
    private Cliente cliente; // Cliente que realiza el pedido
    private Articulo articulo; // Artículo solicitado
    private int cantidad; // Número de unidades
    private LocalDateTime fechaHoraPedido; // Fecha y hora del pedido

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
     * Devuelve una representación en texto del pedido con sus datos clave.
     *
     * @return Información del pedido como cadena.
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
