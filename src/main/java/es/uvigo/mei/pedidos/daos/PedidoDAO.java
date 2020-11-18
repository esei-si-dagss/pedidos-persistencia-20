package es.uvigo.mei.pedidos.daos;

import es.uvigo.mei.pedidos.entidades.Pedido;
import java.util.List;

public interface PedidoDAO extends GenericoDAO<Pedido, Long> {
    public Pedido buscarPorClaveConLineasPedido(Long clave);
    public List<Pedido> buscarPorCliente(String DNI);
    public List<Pedido> buscarPorClienteConLineasPedido(String DNI);
}
