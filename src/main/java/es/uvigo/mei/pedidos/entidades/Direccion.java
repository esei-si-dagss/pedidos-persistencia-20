package es.uvigo.mei.pedidos.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Direccion implements Serializable{
    private String domicilio;
    private String localidad;
    private String codigoPostal;
    private String provincia;
    private String telefono;

    public Direccion() {
    }

    public Direccion(String domicilio, String localidad, String codigoPostal, String provincia, String telefono) {
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.codigoPostal = codigoPostal;
        this.provincia = provincia;
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.domicilio);
        hash = 37 * hash + Objects.hashCode(this.localidad);
        hash = 37 * hash + Objects.hashCode(this.codigoPostal);
        hash = 37 * hash + Objects.hashCode(this.provincia);
        hash = 37 * hash + Objects.hashCode(this.telefono);
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
        final Direccion other = (Direccion) obj;
        if (!Objects.equals(this.domicilio, other.domicilio)) {
            return false;
        }
        if (!Objects.equals(this.localidad, other.localidad)) {
            return false;
        }
        if (!Objects.equals(this.codigoPostal, other.codigoPostal)) {
            return false;
        }
        if (!Objects.equals(this.provincia, other.provincia)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Direccion{" + "domicilio=" + domicilio + ", localidad=" + localidad + ", codigoPostal=" + codigoPostal + ", provincia=" + provincia + ", telefono=" + telefono + '}';
    }
    
    
}
