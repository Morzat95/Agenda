package persistencia.dao.interfaz;

import java.util.List;

import dto.TipoContactoDTO;

public interface TipoContactoDAO {
	
	public boolean insert(TipoContactoDTO tipo_de_contacto);

	public boolean delete(TipoContactoDTO tipo_de_contacto_a_eliminar);
	
	public boolean update(TipoContactoDTO tipo_de_contacto_a_modificar);
	
	public List<TipoContactoDTO> readAll();

}
