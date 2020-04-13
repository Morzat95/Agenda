package persistencia.dao.interfaz;

import java.util.List;

import dto.PaísDTO;
import dto.ProvinciaDTO;

public interface ProvinciaDAO extends DAO<ProvinciaDTO> {

	public List<ProvinciaDTO> readBy(PaísDTO país);

}
