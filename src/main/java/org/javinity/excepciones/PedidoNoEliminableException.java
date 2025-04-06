package org.javinity.excepciones;

public class PedidoNoEliminableException extends RuntimeException {
    public PedidoNoEliminableException(String mensaje) {
        super(mensaje);
    }
}
