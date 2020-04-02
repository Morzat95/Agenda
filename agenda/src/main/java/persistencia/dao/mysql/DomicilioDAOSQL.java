package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DomicilioDTO;
import dto.LocalidadDTO;
import dto.PaísDTO;
import dto.ProvinciaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.DomicilioDAO;

public class DomicilioDAOSQL implements DomicilioDAO {
	
	private static int lastId = 1;

	private static final String insert = "INSERT INTO domicilio (idDomicilio, calle, altura, piso, departamento, idLocalidad) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM domicilio WHERE idDomicilio = ?";
	private static final String update = "UPDATE domicilio SET calle = ?, altura = ?, piso = ?, departamento = ?, idLocalidad = ? WHERE idDomicilio = ?";
	private static final String readall = "SELECT * FROM domicilio";
	
	public DomicilioDAOSQL() {
		DomicilioDAOSQL.lastId = getLastId();
	}
	
	private int getLastId() {
		PreparedStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();
		int lastId = 0;
		String readLastId = "SELECT MAX(idDomicilio) AS lastId FROM domicilio;";
		try
		{
			statement = conexion.getSQLConexion().prepareStatement(readLastId);
			resultSet = statement.executeQuery();
			while(resultSet.next())
				lastId = resultSet.getInt("lastId");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
		return lastId;
	}
		
	public boolean insert(DomicilioDTO domicilio)
	{
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			domicilio.setIdDomicilio(++lastId);
			statement.setInt(1, domicilio.getIdDomicilio());
			statement.setString(2, domicilio.getCalle());
			statement.setInt(3, domicilio.getAltura());
			statement.setString(4, domicilio.getPiso());
			statement.setString(5, domicilio.getDepartamento());
			statement.setInt(6, domicilio.getLocalidad().getIdLocalidad());
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
			statement.setInt(5, domicilio_a_editar.getLocalidad().getIdLocalidad());
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

	private DomicilioDTO getDomicilioDTO(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("idDomicilio");
		String calle = resultSet.getString("calle");
		int altura = resultSet.getInt("altura");
		String piso = resultSet.getString("piso");
		String departamento = resultSet.getString("departamento");
		int idLocalidad = resultSet.getInt("idLocalidad");
	
		return new DomicilioDTO(id, calle, altura, piso, departamento, getLocalidad(idLocalidad));
	}
	
	public LocalidadDTO getLocalidad(int id) throws SQLException {
		PreparedStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();	
		String nombre = "";
		int idProvincia = 0;
		String readSingle = "SELECT * FROM localidades WHERE idLocalidad = " + id + ";";
		try
		{
			statement = conexion.getSQLConexion().prepareStatement(readSingle);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				nombre = resultSet.getString("nombre");
				idProvincia = resultSet.getInt("idProvincia");
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
		return new LocalidadDTO(id, nombre, getProvinciaDTO(idProvincia));
	}
	
	private ProvinciaDTO getProvinciaDTO(int id) {
		PreparedStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();	
		String nombre = "";
		int idPaís = 0;
		String readSingle = "SELECT * FROM países WHERE idPaís = " + id + ";";
		try
		{
			statement = conexion.getSQLConexion().prepareStatement(readSingle);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				nombre = resultSet.getString("nombre");
				idPaís = resultSet.getInt("idPaís");
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
		return new ProvinciaDTO(id, nombre, getPaísDTO(idPaís));
	}
	
	private PaísDTO getPaísDTO(int id) {
		PreparedStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();	
		String nombre = "";
		String readSingle = "SELECT * FROM países WHERE idPaís = " + id + ";";
		try
		{
			statement = conexion.getSQLConexion().prepareStatement(readSingle);
			resultSet = statement.executeQuery();
			
			while (resultSet.next())
				nombre = resultSet.getString("nombre");

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
		return new PaísDTO(id, nombre);
	}

}
