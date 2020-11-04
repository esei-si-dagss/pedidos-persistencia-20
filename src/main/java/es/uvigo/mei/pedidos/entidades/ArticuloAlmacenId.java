package es.uvigo.mei.pedidos.entidades;

import java.io.Serializable;
import java.util.Objects;

public class ArticuloAlmacenId implements Serializable{
    private Long articulo;
    private Long almacen;

    
    public ArticuloAlmacenId() {
		super();
	}

	public ArticuloAlmacenId(Long articulo, Long almacen) {
		super();
		this.articulo = articulo;
		this.almacen = almacen;
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.articulo);
        hash = 97 * hash + Objects.hashCode(this.almacen);
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
        final ArticuloAlmacenId other = (ArticuloAlmacenId) obj;
        if (!Objects.equals(this.articulo, other.articulo)) {
            return false;
        }
        if (!Objects.equals(this.almacen, other.almacen)) {
            return false;
        }
        return true;
    }

    
    
}
