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

}
