package dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Domicilios")
public class DomicilioDTO {
	
	@Id
	@GeneratedValue
	private Long id;
	private String calle;
	private int altura;
	private String piso;
	private String departamento;
	
	@ManyToOne
	private LocalidadDTO localidad;
	
	public DomicilioDTO() {
		
	}
	
	public DomicilioDTO(String calle, int altura, String piso, String departamento) {
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
	}
	
	public DomicilioDTO(String calle, int altura, String piso, String departamento, LocalidadDTO localidad) {
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.departamento = departamento;
		this.localidad = localidad;
	}

	public Long getIdDomicilio() {
		return id;
	}

	public void setIdDomicilio(Long id) {
		this.id = id;
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
