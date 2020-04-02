package dto;

public class ProvinciaDTO {

	private int idProvincia;
	private String nombre;
	private PaísDTO país;
	
	public ProvinciaDTO(int idProvincia, String nombre, PaísDTO país) {
		this.idProvincia = idProvincia;
		this.nombre = nombre;
		this.setPaís(país);
	}
	
	public int getIdProvincia() {
		return idProvincia;
	}
	
	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
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
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idProvincia;
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
		if (idProvincia != other.idProvincia)
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
	
	
	
}
