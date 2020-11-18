package es.uvigo.mei.pedidos.daos;

import es.uvigo.mei.pedidos.entidades.Cliente;
import es.uvigo.mei.pedidos.entidades.Direccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class GenericoDAOJPATest {

    private static EntityManagerFactory EMF;
    private static List<Cliente> ENTIDADES_CREADAS;
    private static Cliente NUEVA_ENTIDAD;
    private static String CLAVE_INEXISTENTE = "99999999Z";

    @BeforeAll
    public static void inicializar() {
        EMF = Persistence.createEntityManagerFactory("pedidos_test_PU");

        List<Cliente> clientes = crearEntidadesIniciales();
        NUEVA_ENTIDAD = clientes.get(clientes.size() - 1);
        ENTIDADES_CREADAS = clientes.subList(0, clientes.size() - 1);

        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        for (Cliente entidad : ENTIDADES_CREADAS) {
            em.persist(entidad);
        }
        em.getTransaction().commit();
        em.close();
    }

    @AfterAll
    public static void terminar() {
        EMF.close();
    }

    private static List<Cliente> crearEntidadesIniciales() {
        List<Cliente> clientes = new ArrayList<>();
        Direccion direccion1 = new Direccion("aaaa", "aaaa", "12345", "aaaa", "111111111");
        Direccion direccion2 = new Direccion("bbbb", "bbbb", "12345", "bbbb", "222222222");

        clientes.add(new Cliente("11111111A", "Cliente1 Cliente1 Cliente1", direccion1));
        clientes.add(new Cliente("22222222B", "Cliente2 Cliente2 Cliente2", direccion1));
        clientes.add(new Cliente("33333333B", "Cliente3 Cliente3 Cliente3", direccion2));
        clientes.add(new Cliente("44444444B", "Cliente4 Cliente4 Cliente4", direccion2));

        return clientes;
    }

    @Test
    public void crearEntidadTest() throws PedidosException {
        GenericoDAO<Cliente, String> dao = new GenericoDAOJPA<>(EMF.createEntityManager(), Cliente.class);
        dao.crear(NUEVA_ENTIDAD);

        // Verificar creación
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        Cliente entidadCreada = em.find(Cliente.class, NUEVA_ENTIDAD.getDNI());
        em.getTransaction().commit();
        em.close();

        assertEquals(NUEVA_ENTIDAD, entidadCreada);
    }

    @Test
    public void buscarEntidadTest() throws PedidosException {
        GenericoDAO<Cliente, String> dao = new GenericoDAOJPA<>(EMF.createEntityManager(), Cliente.class);

        Cliente entidadABuscar = ENTIDADES_CREADAS.get(0);
        String clave = entidadABuscar.getDNI();
        Cliente entidadEncontrada = dao.buscarPorClave(clave);

        assertEquals(entidadABuscar, entidadEncontrada);
    }

    @Test
    public void buscarEntidadInexistenteTest() throws PedidosException {
        GenericoDAO<Cliente, String> dao = new GenericoDAOJPA<>(EMF.createEntityManager(), Cliente.class);

        String clave = CLAVE_INEXISTENTE;
        Cliente entidadEncontrada = dao.buscarPorClave(clave);
        assertNull(entidadEncontrada);
    }

    @Test
    public void crearEntidadRepetidaTest() throws PedidosException {
        GenericoDAO<Cliente, String> dao = new GenericoDAOJPA<>(EMF.createEntityManager(), Cliente.class);

        Cliente entidadExistente = ENTIDADES_CREADAS.get(0);
        assertThrows(PedidosException.class, () -> {
                            dao.crear(entidadExistente);
        });
    }

    @Test
    public void buscarTodosTest() throws PedidosException {
        GenericoDAO<Cliente, String> dao = new GenericoDAOJPA<>(EMF.createEntityManager(), Cliente.class);

        List<Cliente> entidades = dao.buscarTodos();

        int totalEntidades = ENTIDADES_CREADAS.size();
        assertEquals(totalEntidades, entidades.size());
    }

}
