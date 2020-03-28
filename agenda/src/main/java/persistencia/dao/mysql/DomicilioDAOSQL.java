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

import dto.DomicilioDTO;
import dto.PersonaDTO;
import dto.TipoContactoDTO;
import persistencia.conexion.Conexion;

public class DomicilioDAOSQL {

	private static final String insert = "INSERT INTO domicilio (idDomicilio, calle, altura, piso, departamento, idLocalidad) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM domicilio WHERE idDomicilio = ?";
	private static final String update = "UPDATE domicilio SET calle = ?, altura = ?, piso = ?, departamento = ?, idLocalidad = ? WHERE idDomicilio = ?";
	private static final String readall = "SELECT * FROM personas";
	
		
	public boolean insert(DomicilioDTO domicilio)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, domicilio.getIdDomicilio());
			statement.setString(2, domicilio.getCalle());
			statement.setInt(3, domicilio.getAltura());
			statement.setString(4, domicilio.getPiso());
			statement.setString(5, domicilio.getDepartamento());
			statement.setInt(6, domicilio.getIdLocalidad());
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
	
	public boolean delete(DomicilioDTO domicilio_a_eliminar)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, domicilio_a_eliminar.getIdDomicilio());
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
	
	public boolean update(DomicilioDTO domicilio_a_editar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try
		{
			statement = conexion.prepareStatement(update);
			statement.setString(1, domicilio_a_editar.getCalle());
			statement.setInt(2, domicilio_a_editar.getAltura());
			statement.setString(3, domicilio_a_editar.getPiso());
			statement.setString(4, domicilio_a_editar.getDepartamento());
			statement.setInt(5, domicilio_a_editar.getIdLocalidad());
			statement.setInt(6, domicilio_a_editar.getIdDomicilio());
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
	
	public List<DomicilioDTO> readAll()
	{
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<DomicilioDTO> domicilios = new ArrayList<DomicilioDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				domicilios.add(getDomicilioDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return domicilios;
	}

	private DomicilioDTO getDomicilioDTO(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
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
	
	public DomicilioDTO getDomicilio(int id) throws SQLException {
		PreparedStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();	
		
		String calle = "";
		int altura = 0;
		String piso = "";
		String departamento = "";
		int idLocalidad = 0;

		String name = "SELECT * FROM domicilio WHERE idDomicilio = " + id + ";";
		try
		{
			statement = conexion.getSQLConexion().prepareStatement(name);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				calle = resultSet.getString("calle");
				altura = resultSet.getInt("altura");
				piso = resultSet.getString("piso");
				departamento = resultSet.getString("departamento");
				idLocalidad = resultSet.getInt("idLocalidad");
				
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
		return new DomicilioDTO(id, calle, altura, piso, departamento, idLocalidad);
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
		
		DomicilioDTO domicilio = getDomicilio(resultSet.getInt("idDomicilio"));
		return new PersonaDTO(id, nombre, tel, email, fechaCumpleanio, tipoContacto, domicilio);
	}

}
