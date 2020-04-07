package dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Localidades")
public class LocalidadDTO {
	
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	
	@ManyToOne
	private ProvinciaDTO provincia;
	
	@OneToMany ( mappedBy = "localidad", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<DomicilioDTO> domicilios = new ArrayList<DomicilioDTO>();
	
	public LocalidadDTO() {
		
	}
	
	public LocalidadDTO(String nombre) {
		this.nombre = nombre;
	}
	
	public LocalidadDTO(String nombre, ProvinciaDTO provincia) {
		this.nombre = nombre;
		this.provincia = provincia;
	}
	
	public void addDomicilio(DomicilioDTO domicilio) {
		domicilios.add(domicilio);
		domicilio.setLocalidad(this);
	}
	
	public void removeDomicilio(DomicilioDTO domicilio) {
		domicilios.remove(domicilio);
		
		if (domicilio != null)
			domicilio.setLocalidad(null);
	}

	public Long getIdLocalidad() {
		return id;
	}

	public void setIdLocalidad(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ProvinciaDTO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaDTO provincia) {
		this.provincia = provincia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domicilios == null) ? 0 : domicilios.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((provincia == null) ? 0 : provincia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalidadDTO other = (LocalidadDTO) obj;
		if (domicilios == null) {
			if (other.domicilios != null)
				return false;
		} else if (!domicilios.equals(other.domicilios))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (provincia == null) {
			if (other.provincia != null)
				return false;
		} else if (!provincia.equals(other.provincia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return String.format("LocalidadID: %d, LocalidadNombre: %s\n", this.id.intValue(), this.nombre);
		
	}
	
}
