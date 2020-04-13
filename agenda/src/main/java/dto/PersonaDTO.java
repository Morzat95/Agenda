package dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Personas")
public class PersonaDTO 
{
	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	private String telefono;
	private String email;
	private Date fechaCumpleanio;
	
	@OneToOne
	private TipoContactoDTO tipoDeContacto;
	
	@OneToOne ( cascade = CascadeType.ALL, orphanRemoval = true )
	private DomicilioDTO domicilio;
	
	private boolean favorito;
	
	public PersonaDTO() {
		
	}
	
	public PersonaDTO(String nombre, String telefono, String email, Date fechaCumpleanio, boolean favorito)
	{
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.fechaCumpleanio = fechaCumpleanio;
		this.favorito = favorito;
	}
	
	public PersonaDTO(String nombre, String telefono, String email, Date fechaCumpleanio, TipoContactoDTO tipoDeContacto, DomicilioDTO domicilio, boolean favorito)
	{
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.fechaCumpleanio = fechaCumpleanio;
		this.favorito = favorito;
		
		if (tipoDeContacto != null)
			tipoDeContacto.addPersona(this);
		
		this.domicilio = domicilio;
	}
	
	public Long getIdPersona() 
	{
		return this.id;
	}

	public void setIdPersona(Long id) 
	{
		this.id = id;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getTelefono() 
	{
		return this.telefono;
	}

	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public Date getFechaCumpleanio()
	{
		return this.fechaCumpleanio;
	}
	
	public void setFechaCumpleanio(Date fechaCumpleanios)
	{
		this.fechaCumpleanio = fechaCumpleanios;
	}
	
	public TipoContactoDTO getTipoDeContacto()
	{
		return this.tipoDeContacto;
	}
	
	public void setTipoDeContacto(TipoContactoDTO tipoDeContacto)
	{
		this.tipoDeContacto = tipoDeContacto;
	}
	
	public DomicilioDTO getDomicilio() 
	{
		return this.domicilio;
	}
	
	public void setDomicilio(DomicilioDTO domicilio)
	{
		this.domicilio = domicilio;
	}
	
	public boolean getFavorito() 
	{
		return this.favorito;
	}
	
	public void setFavorito(boolean favorito)
	{
		this.favorito = favorito;
	}
}
