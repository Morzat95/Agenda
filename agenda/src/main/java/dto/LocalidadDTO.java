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

	public String toString() {
		return nombre;
	}

	public ProvinciaDTO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaDTO provincia) {
		this.provincia = provincia;
	}
}
