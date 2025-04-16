package org.javinity.dao.interfaces;

import org.javinity.modelos.Pedido;

import java.util.List;

public interface PedidoDAO {
    void insertar(Pedido pedido);
    Pedido buscar(int numPedido);
    List<Pedido> listar();
    void eliminar(int numPedido);
}
