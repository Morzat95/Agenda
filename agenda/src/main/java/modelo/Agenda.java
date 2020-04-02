package modelo;

import java.util.List;

import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaísDTO;
import dto.PersonaDTO;
import dto.TipoContactoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.DomicilioDAO;
import persistencia.dao.interfaz.LocalidadDAO;
import persistencia.dao.interfaz.PaísDAO;
import persistencia.dao.interfaz.PersonaDAO;
import persistencia.dao.interfaz.TipoContactoDAO;


public class Agenda 
{
	private PersonaDAO persona;
	private LocalidadDAO localidad;
	private TipoContactoDAO tipoContacto;
	private DomicilioDAO domicilio;
	private PaísDAO paísDAO;
	
	public Agenda(DAOAbstractFactory metodo_persistencia)
	{
		this.persona = metodo_persistencia.createPersonaDAO();
		this.localidad = metodo_persistencia.createLocalidadDAO();
		this.tipoContacto = metodo_persistencia.createTipoContactoDAO();
		this.domicilio = metodo_persistencia.createDomicilioDAO();
		this.paísDAO = metodo_persistencia.createPaísDAO();
	}
	
	public void agregarPersona(PersonaDTO nuevaPersona)
	{
		this.persona.insert(nuevaPersona);
	}

	public void borrarPersona(PersonaDTO persona_a_eliminar) 
	{
		this.persona.delete(persona_a_eliminar);
	}
	
	public void editarPersona(PersonaDTO persona_a_editar) {
		this.persona.update(persona_a_editar);
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
	
	public void agregarDomicilio(DomicilioDTO nuevoDomicilio)
	{
		this.domicilio.insert(nuevoDomicilio);
	}

	public void borrarDomicilio(DomicilioDTO domicilio_a_aliminar) 
	{
		this.domicilio.delete(domicilio_a_aliminar);
	}
	
	public void modificarDomicilio(DomicilioDTO domicilio_a_editar) {
		this.domicilio.update(domicilio_a_editar);
	}
	
	public List<DomicilioDTO> obtenerDomicilios()
	{
		return this.domicilio.readAll();		
	}
	
	public void agregarPaís(PaísDTO nuevoPaís) {
		this.paísDAO.insert(nuevoPaís);
	}
	
	public void modificarPaís(PaísDTO país_a_modificar) {
		this.paísDAO.update(país_a_modificar);
	}
	
	public void borrarPaís(PaísDTO país_a_eliminar) {
		this.paísDAO.delete(país_a_eliminar);
	}
	
	public List<PaísDTO> obtenerPaíses() {
		return this.paísDAO.readAll();
	}
	
}
