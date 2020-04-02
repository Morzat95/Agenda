package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PaísDTO;
import dto.ProvinciaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ProvinciaDAO;

public class ProvinciaDAOSQL implements ProvinciaDAO {
	
	private static int lastId = 1;
	
	private static final String insert = "INSERT INTO provincias(idProvincia, nombre, idPaís) VALUES(?, ?, ?)";
	private static final String delete = "DELETE FROM provincias WHERE idProvincia = ?";
	private static final String update = "UPDATE provincias SET nombre = ?, idPaís = ? WHERE idProvincia = ?";
	private static final String readall = "SELECT * FROM provincias";
	private static final String hasData = "SELECT EXISTS (SELECT 1 FROM provincias)";
	
	public ProvinciaDAOSQL() {
		ProvinciaDAOSQL.lastId = getLastId();
	}
	
	private int getLastId() {
		PreparedStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();
		int lastId = 0;
		String readLastId = "SELECT MAX(idPaís) AS lastId FROM provincias;";
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
	
	@Override
	public boolean insert(ProvinciaDTO provincia) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			provincia.setIdProvincia(++lastId);
			statement.setInt(1, provincia.getIdProvincia());
			statement.setString(2, provincia.getNombre());
			statement.setInt(3, provincia.getPaís().getIdPaís());
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
	
	@Override
	public boolean delete(ProvinciaDTO provincia_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, provincia_a_eliminar.getIdProvincia());
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
	
	@Override
	public boolean update(ProvinciaDTO provincia_a_modificar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try
		{
			statement = conexion.prepareStatement(update);
			statement.setString(1, provincia_a_modificar.getNombre());
			statement.setInt(2, provincia_a_modificar.getPaís().getIdPaís());
			statement.setInt(3, provincia_a_modificar.getIdProvincia());
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
	
	@Override
	public List<ProvinciaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<ProvinciaDTO> países = new ArrayList<ProvinciaDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				países.add(getPprovinciaDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return países;
	}
	
	private ProvinciaDTO getPprovinciaDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("idProvincia");
		String nombre = resultSet.getString("nombre");
		int idPaís = resultSet.getInt("idPaís");
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
	
	@Override
	public boolean hasData() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		boolean dataExists = false;
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(hasData);
			resultSet = statement.executeQuery();
			resultSet.next();
			dataExists = resultSet.getInt(1) == 1 ? true : false;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return dataExists;
	}

}
