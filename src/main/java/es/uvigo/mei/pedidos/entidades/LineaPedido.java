package es.uvigo.mei.pedidos.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LineaPedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroLineaPedido;

    @ManyToOne
    private Pedido pedido;

    private int cantidad;

    @ManyToOne
    private Articulo articulo;

    private Double precioVenta;

    public LineaPedido() {
    }

    public LineaPedido(Pedido pedido, int cantidad, Articulo articulo) {
        this.pedido = pedido;
        this.cantidad = cantidad;
        this.articulo = articulo;
        this.precioVenta = articulo.getPrecioUnitario();
    }

    public LineaPedido(Pedido pedido, int cantidad, Articulo articulo, Double precioVenta) {
        this.pedido = pedido;
        this.cantidad = cantidad;
        this.articulo = articulo;
        this.precioVenta = precioVenta;
    }

    public Long getNumeroLineaPedido() {
        return numeroLineaPedido;
    }

    public void setNumeroLineaPedido(Long numeroLineaPedido) {
        this.numeroLineaPedido = numeroLineaPedido;
    }


    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido nuevoPedido) {
        if (this.pedido != null) {
            this.pedido.eliminarLineaPedidoInterno(this);
        }
        this.pedido = nuevoPedido;
        if (nuevoPedido != null) {
            nuevoPedido.anadirLineaPedidoInterno(this);
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getImporteTotal() {
        return (this.cantidad * this.precioVenta);
    }

    @Override
    public int hashCode() {
        if (this.numeroLineaPedido != null) {
            return this.numeroLineaPedido.hashCode();
        }
        
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.pedido);
        hash = 23 * hash + this.cantidad;
        hash = 23 * hash + Objects.hashCode(this.articulo);
        hash = 23 * hash + Objects.hashCode(this.precioVenta);
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
        final LineaPedido other = (LineaPedido) obj;
        if (this.numeroLineaPedido != null) {
            return this.numeroLineaPedido.equals(other.getNumeroLineaPedido());
        }
        if (this.cantidad != other.cantidad) {
            return false;
        }
        if (!Objects.equals(this.pedido, other.pedido)) {
            return false;
        }
        if (!Objects.equals(this.articulo, other.articulo)) {
            return false;
        }
        if (!Objects.equals(this.precioVenta, other.precioVenta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LineaPedido{" + "numeroLineaPedido=" + numeroLineaPedido + ", cantidad=" + cantidad + ", articulo=" + articulo + ", precioVenta=" + precioVenta + '}';
    }

}
