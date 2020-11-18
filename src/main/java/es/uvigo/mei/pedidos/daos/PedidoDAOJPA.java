package es.uvigo.mei.pedidos.daos;

import es.uvigo.mei.pedidos.entidades.Pedido;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class PedidoDAOJPA extends GenericoDAOJPA<Pedido, Long> implements PedidoDAO {

    public PedidoDAOJPA(EntityManager em) {
        super(em, Pedido.class);
    }

    @Override
    public Pedido buscarPorClaveConLineasPedido(Long clave) {
        Pedido resultado = null;
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            resultado = em.find(Pedido.class, clave);
            resultado.getLineas();  // Fuerza carga de lineas de pedido (sólo dentro de la transacción)
            tx.commit();
        } catch (RuntimeException rte) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
        }
        return resultado;
    }

    @Override
    public List<Pedido> buscarPorCliente(String dni) {
        List<Pedido> resultado = Collections.emptyList();
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido AS p WHERE p.cliente.DNI = :dni", Pedido.class);
            query.setParameter("dni", dni);
            resultado = query.getResultList();
            tx.commit();
        } catch (RuntimeException rte) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
        }
        return resultado;    
    }

    @Override
    public List<Pedido> buscarPorClienteConLineasPedido(String dni) {
        List<Pedido> resultado = Collections.emptyList();
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido AS p FETCH JOIN p.lineas AS ls WHERE p.cliente.DNI = :dni", Pedido.class);
            query.setParameter("dni", dni);
            resultado = query.getResultList();
            tx.commit();
        } catch (RuntimeException rte) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
        }
        return resultado;    
    }

}
