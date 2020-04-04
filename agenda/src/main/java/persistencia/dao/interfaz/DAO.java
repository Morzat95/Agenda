package persistencia.dao.interfaz;

import java.util.List;

public interface DAO<T> {
	
	public T insert(T entity);

	public T update(T entity);
	
	public void delete(T entity);
	
	public T byId(long id);
	
	public List<T> readAll();
	
	public boolean hasData();
	
	public void cerrar();

}
