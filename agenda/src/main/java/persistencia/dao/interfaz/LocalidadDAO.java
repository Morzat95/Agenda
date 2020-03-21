package persistencia.dao.interfaz;

import java.util.List;

public interface LocalidadDAO {
	
	public boolean insert(LocalidadDAO localidad);

	public boolean delete(LocalidadDAO localidad_a_eliminar);
	
	public boolean update(LocalidadDAO localidad_a_modificar);
	
	public List<LocalidadDAO> readAll();

}
