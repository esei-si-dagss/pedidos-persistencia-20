package es.uvigo.mei.pedidos.daos;

import java.util.List;

public interface GenericoDAO<T,K> {
    public T crear(T entidad) throws PedidosException ;
    public T actualizar(T entidad) throws PedidosException ;
    public void eliminar(T entidad) throws PedidosException ;
    public T buscarPorClave(K clave) throws PedidosException ;
    public List<T> buscarTodos() throws PedidosException ;
    public Long contarTodos() throws PedidosException ;
}
