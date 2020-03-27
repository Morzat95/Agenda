package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.log.SysoCounter;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PersonaDAO;
import dto.PersonaDTO;
import dto.TipoContactoDTO;

public class PersonaDAOSQL implements PersonaDAO
{
	private static final String insert = "INSERT INTO personas(idPersona, nombre, telefono, email, fechaCumpleaños, idTipoDeContacto) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM personas WHERE idPersona = ?";
	private static final String update = "UPDATE personas SET nombre = ?, telefono = ?, email = ?, fechaCumpleaños = ?, idTipoDeContacto = ? WHERE idPersona = ?";
	private static final String readall = "SELECT * FROM personas";
	
		
	public boolean insert(PersonaDTO persona)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, persona.getIdPersona());
			statement.setString(2, persona.getNombre());
			statement.setString(3, persona.getTelefono());
			statement.setString(4, persona.getEmail());
			statement.setDate(5, new Date(persona.getFechaCumpleanio().getTime()));
			statement.setInt(6, persona.getTipoDeContacto().getIdTipoContacto());
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isInsertExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isInsertExitoso;
	}
	
	public boolean delete(PersonaDTO persona_a_eliminar)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(persona_a_eliminar.getIdPersona()));
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isdeleteExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return isdeleteExitoso;
	}
	
	public boolean update(PersonaDTO persona_a_editar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try
		{
			statement = conexion.prepareStatement(update);
			statement.setString(1, persona_a_editar.getNombre());
			statement.setString(2, persona_a_editar.getTelefono());
			statement.setString(3, persona_a_editar.getEmail());
			statement.setDate(4, new Date(persona_a_editar.getFechaCumpleanio().getTime()));
			statement.setInt(5, persona_a_editar.getTipoDeContacto().getIdTipoContacto());
			statement.setInt(6, persona_a_editar.getIdPersona());
			if(statement.executeUpdate() > 0)
			{
				conexion.commit();
				isUpdateExitoso = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return isUpdateExitoso;
	}
	
	public List<PersonaDTO> readAll()
	{
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PersonaDTO> personas = new ArrayList<PersonaDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				personas.add(getPersonaDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return personas;
	}

	public String getTipoContacto(int id) throws SQLException {
		PreparedStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();
		
		String nombre = "";
		String name = "SELECT nombre FROM tipos_de_contacto WHERE idTipoContacto = " + id + ";";

		try
		{
			statement = conexion.getSQLConexion().prepareStatement(name);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				nombre = resultSet.getString("nombre");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
		return nombre;
	}
	
	private PersonaDTO getPersonaDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("idPersona");
		String nombre = resultSet.getString("Nombre");
		String tel = resultSet.getString("Telefono");
		String email = resultSet.getString("Email");
		
		String stringFecha = resultSet.getString("FechaCumpleaños");
		java.util.Date fechaCumpleanio = null;
		try {
			fechaCumpleanio = new SimpleDateFormat("yyyy-MM-dd").parse(stringFecha);
		} catch (ParseException e) { e.printStackTrace(); }
		
		int idContacto = resultSet.getInt("idTipoDeContacto");
		TipoContactoDTO tipoContacto = new TipoContactoDTO(idContacto, getTipoContacto(idContacto));
		
		return new PersonaDTO(id, nombre, tel, email, fechaCumpleanio, tipoContacto);
	}

}
