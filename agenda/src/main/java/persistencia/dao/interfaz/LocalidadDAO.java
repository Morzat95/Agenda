package persistencia.dao.interfaz;

import java.util.List;

import dto.LocalidadDTO;
import dto.ProvinciaDTO;

public interface LocalidadDAO {
	
	public boolean insert(LocalidadDTO localidad);

	public boolean delete(LocalidadDTO localidad_a_eliminar);
	
	public boolean update(LocalidadDTO localidad_a_modificar);
	
	public List<LocalidadDTO> readAll();
	
	public boolean hasData();
	
	public List<LocalidadDTO> readBy(ProvinciaDTO provincia);

}
