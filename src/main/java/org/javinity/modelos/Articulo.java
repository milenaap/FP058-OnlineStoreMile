package org.javinity.modelos;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

/**
 * Entidad JPA que representa un artículo en la tienda online.
 * @author Javinity
 */

@Entity
@Table(name = "articulos")
public class Articulo {

    @Id
    @Column(name = "codigo_producto")
    private String codigoProducto;

    private String descripcion;

    @Column(name = "precio_venta")
    private float precioVenta;

    @Column(name = "gastos_envio")
    private float gastosEnvio;

    @Column(name = "tiempo_prep_envio")
    private int tiempoPrepEnvio;;

    public Articulo() {}

    public Articulo(String codigoProducto, String descripcion, float precioVenta, float gastosEnvio, int tiempoPrepEnvio) {
        this.codigoProducto = codigoProducto;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPrepEnvio = tiempoPrepEnvio;
    }

    public Articulo(String codigoProducto) {
        this.codigoProducto = codigoProducto;
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
