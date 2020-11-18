package es.uvigo.mei.pedidos.daos;

import es.uvigo.mei.pedidos.entidades.Articulo;
import es.uvigo.mei.pedidos.entidades.Cliente;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class ClienteDAOJPA extends GenericoDAOJPA<Cliente, String> implements ClienteDAO {

    public ClienteDAOJPA(EntityManager em) {
        super(em, Cliente.class);
    }

    @Override
    public List<Cliente> buscarPorLocalidad(String localidad) throws PedidosException {
        List<Cliente> resultado = Collections.emptyList();
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente AS c WHERE c.direccion.localidad = :localidad", Cliente.class);
            query.setParameter("localidad", localidad);
            resultado = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
            throw new PedidosException("Excepción buscando por localidad", e);
        }
        return resultado;
    }

    @Override
    public List<Cliente> buscarPorNombre(String patron) throws PedidosException {
        List<Cliente> resultado = Collections.emptyList();
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente AS c WHERE c.nombre LIKE :patron", Cliente.class);
            query.setParameter("patron", "%" + patron + "%");
            resultado = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
            throw new PedidosException("Excepción buscando por nombre", e);
        }
        return resultado;
    }

}
