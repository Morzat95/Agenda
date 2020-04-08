package persistencia.dao.interfaz;

import java.util.List;

import dto.PaísDTO;
import dto.ProvinciaDTO;

public interface ProvinciaDAO {
	
	public boolean insert(ProvinciaDTO provincia);

	public boolean delete(ProvinciaDTO provincia_a_eliminar);
	
	public boolean update(ProvinciaDTO provincia_a_modificar);
	
	public List<ProvinciaDTO> readAll();
	
	public boolean hasData();
	
	public List<ProvinciaDTO> readBy(PaísDTO país);

}
