package dto;

public class DomicilioDTO {
	
	private int idDomicilio;
	private String calle;
	private int altura;
	private String piso;
	private String departamento;
	private LocalidadDTO localidad;
	private PersonaDTO persona;
	
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
		return this.idDomicilio;
	}

	public void setIdDomicilio(int idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getCalle() {
		return this.calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getAltura() {
		return this.altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getPiso() {
		return this.piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public LocalidadDTO getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(LocalidadDTO localidad) {
		this.localidad = localidad;
	}
	
	public PersonaDTO getPersona() {
		return this.persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

}
