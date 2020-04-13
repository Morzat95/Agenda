package persistencia.dao.interfaz;

import java.util.List;

import dto.LocalidadDTO;
import dto.ProvinciaDTO;

public interface LocalidadDAO extends DAO<LocalidadDTO> {
	
	public List<LocalidadDTO> readBy(ProvinciaDTO provincia);

}
