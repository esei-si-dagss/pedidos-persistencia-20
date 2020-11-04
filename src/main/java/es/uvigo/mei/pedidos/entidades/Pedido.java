package es.uvigo.mei.pedidos.entidades;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("numeroLineaPedido asc")
    private List<LineaPedido> lineas = new ArrayList<>();

    public Pedido() {
        this.fecha = Date.from(Instant.now());
        this.estado = EstadoPedido.PENDIENTE;
    }

    public Pedido(Date fecha, Cliente cliente) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.fecha = Date.from(Instant.now());
        this.estado = EstadoPedido.PENDIENTE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public void anadirLineaPedido(LineaPedido nuevaLineaPedido) {
        nuevaLineaPedido.setPedido(this);
        // setPedido() llamará a anadirLineaPedidoInterno()
    }

    // Acceso relaciones bidireccionales
    // https://xebia.com/blog/jpa-implementation-patterns-bidirectional-assocations/
    protected void anadirLineaPedidoInterno(LineaPedido nuevaLineaPedido) {
        if (lineas == null) {
            this.lineas = new ArrayList<>();
        }
        if (!this.lineas.contains(nuevaLineaPedido)) {
            this.lineas.add(nuevaLineaPedido);
        }
    }

    public void eliminarLineaPedido(LineaPedido lineaPedido) {
        lineaPedido.setPedido(null);
        // setPedido() llamará a eliminarLineaPedidoInterno()
    }

    protected void eliminarLineaPedidoInterno(LineaPedido lineaPedido) {
        if (lineas != null) {
            this.lineas.remove(lineaPedido);
        }
    }

    public double getImporteTotal() {
        double total = 0.0;
        if (this.lineas != null) {
            for (LineaPedido linea : this.lineas) {
                total += linea.getImporteTotal();
            }
        }
        return total;
    }

    @Override
    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        }
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.fecha);
        hash = 29 * hash + Objects.hashCode(this.estado);
        hash = 29 * hash + Objects.hashCode(this.cliente);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pedido other = (Pedido) obj;
        if (this.id != null) {
            return this.id.equals(other.getId());
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", fecha=" + fecha + ", estado=" + estado + ", cliente=" + cliente + '}';
    }

}
