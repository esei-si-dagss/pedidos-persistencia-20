package es.uvigo.mei.pedidos.daos;

import es.uvigo.mei.pedidos.entidades.Cliente;
import java.util.List;

public interface ClienteDAO extends GenericoDAO<Cliente, String>{
    public List<Cliente> buscarPorLocalidad(String localidad) throws PedidosException;
    public List<Cliente> buscarPorNombre(String patron)  throws PedidosException;
}
