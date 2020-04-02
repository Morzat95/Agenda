package persistencia.dao.interfaz;

import java.util.List;

import dto.PaísDTO;

public interface PaísDAO {

	public boolean insert(PaísDTO país);

	public boolean delete(PaísDTO país_a_eliminar);
	
	public boolean update(PaísDTO país_a_modificar);
	
	public List<PaísDTO> readAll();
	
	public boolean hasData();
	
}
