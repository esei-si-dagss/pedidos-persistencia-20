package es.uvigo.mei.pedidos.daos;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class GenericoDAOJPA<T, K> implements GenericoDAO<T, K> {

    protected EntityManager em;
    protected Class<T> tipoEntidad;

    public GenericoDAOJPA(EntityManager em, Class<T> tipoEntidad) {
        this.em = em;
        this.tipoEntidad = tipoEntidad;
    }

    @Override
    public T crear(T entidad) throws PedidosException {
        T resultado = null;
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            em.persist(entidad);
            resultado = entidad;
            tx.commit();
        } catch (Exception e) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
            throw new PedidosException("Excepción creado entidad", e);
        }
        return resultado;
    }

    @Override
    public T actualizar(T entidad) throws PedidosException {
        T resultado = null;
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            resultado = em.merge(entidad);
            tx.commit();
        } catch (Exception e) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
            throw new PedidosException("Excepción modificando entidad", e);
        }
        return resultado;
    }

    @Override
    public void eliminar(T entidad) throws PedidosException {
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(entidad));
            tx.commit();
        } catch (RuntimeException e) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
            throw new PedidosException("Excepción eliminando entidad", e);
        }
    }

    @Override
    public T buscarPorClave(K clave) throws PedidosException {
        T resultado = null;
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();
            resultado = em.find(tipoEntidad, clave);
            tx.commit();
        } catch (Exception e) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
            throw new PedidosException("Excepción buscando por clave", e);
        }
        return resultado;
    }

    @Override
    public List<T> buscarTodos() {
        List<T> resultado = Collections.emptyList();
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(this.tipoEntidad);
            query.select(query.from(this.tipoEntidad));
            resultado = em.createQuery(query).getResultList();

            tx.commit();
        } catch (RuntimeException rte) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
        }
        return resultado;
    }

    @Override
    public Long contarTodos() throws PedidosException {
        Long resultado = null;
        EntityTransaction tx = this.em.getTransaction();
        try {
            tx.begin();

            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = builder.createQuery(Long.class);
            query.select(builder.count(query.from(this.tipoEntidad)));
            resultado = em.createQuery(query).getSingleResult();

            tx.commit();
        } catch (Exception e) {
            if ((tx != null) && (tx.isActive())) {
                tx.rollback();
            }
            throw new PedidosException("Excepción buscando todas", e);
        }
        return resultado;
    }
}
