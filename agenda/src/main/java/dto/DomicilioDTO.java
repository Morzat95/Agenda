package dto;

public class DomicilioDTO {
	
	private int idDomicilio;
	private String calle;
	private int altura;
	private String piso;
	private String departamento;
	private LocalidadDTO localidad;
	
	public DomicilioDTO(int idDomicilio, String calle, int altura, String piso, String departamento, LocalidadDTO localidad) {
		this.idDomicilio = idDomicilio;
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
		this.localidad = localidad;
	}
	
	public DomicilioDTO(String calle, int altura, String piso, String departamento, LocalidadDTO localidad) {
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
		this.localidad = localidad;
	}

	public int getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(int idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public LocalidadDTO getLocalidad() {
		return localidad;
	}

	public void setLocalidad(LocalidadDTO localidad) {
		this.localidad = localidad;
	}

}
