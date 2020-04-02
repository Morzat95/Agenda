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
	
}
