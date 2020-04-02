package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PaísDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PaísDAO;

public class PaísDAOSQL implements PaísDAO {
	
	private static int lastId = 1;
	
	private static final String insert = "INSERT INTO países(idPaís, nombre) VALUES(?, ?)";
	private static final String delete = "DELETE FROM países WHERE idPaís = ?";
	private static final String update = "UPDATE países SET nombre = ? WHERE idPaís = ?";
	private static final String readall = "SELECT * FROM países ORDER BY nombre";
	private static final String hasData = "SELECT EXISTS (SELECT 1 FROM países)";
	
	public PaísDAOSQL() {
		PaísDAOSQL.lastId = getLastId();
	}
	
	private int getLastId() {
		PreparedStatement statement;
		ResultSet resultSet = null;
		Conexion conexion = Conexion.getConexion();
		int lastId = 0;
		String readLastId = "SELECT MAX(idPaís) AS lastId FROM países;";
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
	public boolean insert(PaísDTO país) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			país.setIdPaís(++lastId);
			statement.setInt(1, país.getIdPaís());
			statement.setString(2, país.getNombre());
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
	public boolean delete(PaísDTO país_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setInt(1, país_a_eliminar.getIdPaís());
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
	public boolean update(PaísDTO país_a_modificar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try
		{
			statement = conexion.prepareStatement(update);
			statement.setString(1, país_a_modificar.getNombre());
			statement.setInt(2, país_a_modificar.getIdPaís());
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
	public List<PaísDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<PaísDTO> países = new ArrayList<PaísDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				países.add(getPaísDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return países;
	}
	
	private PaísDTO getPaísDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("idPaís");
		String nombre = resultSet.getString("nombre");
		return new PaísDTO(id, nombre);
	}
	
	@Override
	public boolean hasData()
	{
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
