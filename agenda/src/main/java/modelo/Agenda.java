package modelo;

import java.util.List;

import dto.LocalidadDTO;
import dto.PersonaDTO;
import dto.TipoContactoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LocalidadDAO;
import persistencia.dao.interfaz.PersonaDAO;
import persistencia.dao.interfaz.TipoContactoDAO;


public class Agenda 
{
	private PersonaDAO persona;
	private LocalidadDAO localidad;
	private TipoContactoDAO tipoContacto;
	
	public Agenda(DAOAbstractFactory metodo_persistencia)
	{
		this.persona = metodo_persistencia.createPersonaDAO();
		this.localidad = metodo_persistencia.createLocalidadDAO();
		this.tipoContacto = metodo_persistencia.createTipoContactoDAO();
	}
	
	public void agregarPersona(PersonaDTO nuevaPersona)
	{
		this.persona.insert(nuevaPersona);
	}

	public void borrarPersona(PersonaDTO persona_a_eliminar) 
	{
		this.persona.delete(persona_a_eliminar);
	}
	
	public List<PersonaDTO> obtenerPersonas()
	{
		return this.persona.readAll();		
	}
	
	public void agregarLocalidad(LocalidadDTO nuevaLocalidad) {
		this.localidad.insert(nuevaLocalidad);
	}
	
	public void borrarLocalidad(LocalidadDTO localidad_a_eliminar) 
	{
		this.localidad.delete(localidad_a_eliminar);
	}
	
	public void modificarLocalidad(LocalidadDTO localidad_a_modificar) 
	{
		this.localidad.update(localidad_a_modificar);
	}
	
	public List<LocalidadDTO> obtenerLocalidades()
	{
		return this.localidad.readAll();		
	}
	
	public void agregarTipoContacto(TipoContactoDTO nuevaLocalidad) {
		this.tipoContacto.insert(nuevaLocalidad);
	}
	
	public void borrarTipoContacto(TipoContactoDTO localidad_a_eliminar) 
	{
		this.tipoContacto.delete(localidad_a_eliminar);
	}
	
	public void modificarTipoContacto(TipoContactoDTO localidad_a_modificar) 
	{
		this.tipoContacto.update(localidad_a_modificar);
	}
	
	public List<TipoContactoDTO> obtenerTiposContacto()
	{
		return this.tipoContacto.readAll();		
	}
	
}
