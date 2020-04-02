package dto;

public class PaísDTO {
	
	private int idPaís;
	private String nombre;
	
	public PaísDTO(int idPaís, String nombre) {
		this.idPaís = idPaís;
		this.nombre = nombre;
	}

	public int getIdPaís() {
		return idPaís;
	}

	public void setIdPaís(int idPais) {
		this.idPaís = idPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPaís;
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
		if (idPaís != other.idPaís)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	

}
