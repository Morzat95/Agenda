package dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Países")
public class PaísDTO {
	
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	
	@OneToMany (mappedBy = "país", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProvinciaDTO> provincias = new ArrayList<ProvinciaDTO>();
	
	public PaísDTO() {
		
	}
	
	public PaísDTO(String nombre) {
		this.nombre = nombre;
	}
	
	public void addProvincia(ProvinciaDTO provincia) {
		provincias.add(provincia);
		provincia.setPaís(this);
	}
	
	public void removeProvincia(ProvinciaDTO provincia) {
		provincias.remove(provincia);
		provincia.setPaís(null);
	}

	public Long getIdPaís() {
		return id;
	}

	public void setIdPaís(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<ProvinciaDTO> getProvincias() {
		return this.provincias;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		PaísDTO other = (PaísDTO) obj;
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
		return true;
	}

	@Override
	public String toString() {
		String result = String.format("PaísID: %d, PaísNombre: %s\n", this.id.intValue(), this.nombre);
		
		for (ProvinciaDTO provincia : provincias)
			result += String.format("-> %s\n", provincia.toString());	
		
		return result;
	}

}
