package org.javinity.modelos;

public class Articulo {

    private String codigoProducto;
    private String descripcion;
    private float precioVenta;
    private float gastosEnvio;
    private int tiempoPrepEnvio;

    public Articulo(String codigoProducto, String descripcion, float precioVenta, float gastosEnvio, int tiempoPrepEnvio) {
        this.codigoProducto = codigoProducto;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPrepEnvio = tiempoPrepEnvio;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(float gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public int getTiempoPrepEnvio() {
        return tiempoPrepEnvio;
    }

    public void setTiempoPrepEnvio(int tiempoPrepEnvio) {
        this.tiempoPrepEnvio = tiempoPrepEnvio;
    }

    @Override
    public String toString() {
        return "Código: " + codigoProducto +
                ", Descripción: " + descripcion +
                ", Precio: " + precioVenta + " €" +
                ", Envío: " + gastosEnvio + " €" +
                ", Tiempo preparación: " + tiempoPrepEnvio + " min";
    }
}
