package dto;

public class LocalidadDTO {
	
	private int idLocalidad;
	private String nombre;
	private ProvinciaDTO provincia;
	
	public LocalidadDTO(int idLocalidad, String nombre, ProvinciaDTO provincia) {
		this.idLocalidad = idLocalidad;
		this.nombre = nombre;
		this.provincia = provincia;
	}

	public int getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
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
		result = prime * result + idLocalidad;
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
		if (idLocalidad != other.idLocalidad)
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
		return String.format("ProvinciaID: %d, ProvinciaNombre: %s\n - %s", this.idLocalidad, this.nombre, this.provincia.toString());
	}
	
}
