package persistencia.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TipoContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TipoContactoDAO;

public class TipoContactoDAOSQL implements TipoContactoDAO {
	
	private static final String insert = "INSERT INTO tipos_de_contacto(idTipoContacto, nombre) VALUES(?, ?)";
	private static final String delete = "DELETE FROM tipos_de_contacto WHERE idTipoContacto = ?";
	private static final String update = "UPDATE tipos_de_contacto SET nombre = ? WHERE idTipoContacto = ?";
	private static final String readall = "SELECT * FROM tipos_de_contacto";

	@Override
	public boolean insert(TipoContactoDTO tipo_de_contacto) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isInsertExitoso = false;
		try
		{
			statement = conexion.prepareStatement(insert);
			statement.setInt(1, tipo_de_contacto.getIdTipoContacto());
			statement.setString(2, tipo_de_contacto.getNombre());
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
	public boolean delete(TipoContactoDTO tipo_de_contacto_a_eliminar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isdeleteExitoso = false;
		try 
		{
			statement = conexion.prepareStatement(delete);
			statement.setString(1, Integer.toString(tipo_de_contacto_a_eliminar.getIdTipoContacto()));
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
	public boolean update(TipoContactoDTO tipo_de_contacto_a_modificar) {
		PreparedStatement statement;
		Connection conexion = Conexion.getConexion().getSQLConexion();
		boolean isUpdateExitoso = false;
		try
		{
			statement = conexion.prepareStatement(update);
			statement.setString(1, tipo_de_contacto_a_modificar.getNombre());
			statement.setInt(2, tipo_de_contacto_a_modificar.getIdTipoContacto());
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
	public List<TipoContactoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<TipoContactoDTO> tipos_de_contactos = new ArrayList<TipoContactoDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				tipos_de_contactos.add(getTipoContactoDTO(resultSet));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return tipos_de_contactos;
	}
	
	private TipoContactoDTO getTipoContactoDTO(ResultSet resultSet) throws SQLException
	{
		int id = resultSet.getInt("idTipoContacto");
		String nombre = resultSet.getString("Nombre");
		return new TipoContactoDTO(id, nombre);
	}

}
