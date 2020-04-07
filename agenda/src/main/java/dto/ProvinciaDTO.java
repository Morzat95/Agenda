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
@Table(name = "Provincias")
public class ProvinciaDTO {

	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	
	@ManyToOne
	private PaísDTO país;
	
	@OneToMany ( mappedBy = "provincia", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();
	
	public ProvinciaDTO() {
		
	}
	
	public ProvinciaDTO(String nombre) {
		this.nombre = nombre;
	}
	
	public ProvinciaDTO(String nombre, PaísDTO país) {
		this.nombre = nombre;
		this.país = país;
	}
	
	public void addLocalidad(LocalidadDTO localidad) {
		localidades.add(localidad);
		localidad.setProvincia(this);
	}
	
	public void removeLocalidad(LocalidadDTO localidad) {
		localidades.remove(localidad);
		localidad.setProvincia(null);
	}
	
	public Long getIdProvincia() {
		return id;
	}
	
	public void setIdProvincia(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public PaísDTO getPaís() {
		return país;
	}

	public void setPaís(PaísDTO país) {
		this.país = país;
//		país.provincias.add(this);
	}
	
	public List<LocalidadDTO> getLocalidades() {
		return this.localidades;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((país == null) ? 0 : país.hashCode());
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
		ProvinciaDTO other = (ProvinciaDTO) obj;
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
		if (país == null) {
			if (other.país != null)
				return false;
		} else if (!país.equals(other.país))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		String result = String.format("ProvinciaID: %d, ProvinciaNombre: %s\n", this.id.intValue(), this.nombre);
		
		for (LocalidadDTO localidad : localidades)
			result += String.format("-> %s", localidad.toString());
		
		return result;
		
	}
	
}
