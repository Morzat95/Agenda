package dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Tipos_de_Contacto")
public class TipoContactoDTO {
	
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	
	@OneToMany ( mappedBy = "tipoDeContacto" )
	private List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
	
	public TipoContactoDTO() {
		
	}
	
	public TipoContactoDTO(String nombre) {
		this.nombre = nombre;
	}
	
	public void addPersona(PersonaDTO persona) {
		if (persona != null) {
			this.personas.add(persona);			
			persona.setTipoDeContacto(this);
		}
	}
	
	public void removePersona(PersonaDTO persona) {
		if (persona != null) {
			this.personas.remove(persona);
			persona.setTipoDeContacto(null);
		}
	}

	public Long getIdTipoContacto() {
		return id;
	}

	public void setIdTipoContacto(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return nombre;
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
		TipoContactoDTO other = (TipoContactoDTO) obj;
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
	
}
