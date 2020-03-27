package dto;

import java.util.Date;

public class PersonaDTO 
{
	private int idPersona;
	private String nombre;
	private String telefono;
	private String email;
	private Date fechaCumpleanio; 
	private TipoContactoDTO tipoDeContacto;
	//private DomicilioDTO domicilio;
	
	public PersonaDTO(int idPersona, String nombre, String telefono, String email, Date fechaCumpleanio, TipoContactoDTO tipoDeContacto)
	{
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.fechaCumpleanio = fechaCumpleanio;
		this.tipoDeContacto = tipoDeContacto;
	}	
	
	public int getIdPersona() 
	{
		return this.idPersona;
	}

	public void setIdPersona(int idPersona) 
	{
		this.idPersona = idPersona;
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
}
