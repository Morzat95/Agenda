package persistencia.dao.mysql;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Table;

import persistencia.dao.interfaz.DAO;
import persistencia.dao.interfaz.EntityManagers;

public class DAOJPA<T> implements DAO<T> {
	
	protected EntityManager entityManager;
	
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public DAOJPA() {
		
		entityManager = EntityManagers.createEntityManagers();
		
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
		
	}

	@Override
	public T insert(T entity) {
		
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		
		return entity;
		
	}
	
	@Override
	public T update(T entity) {

		entityManager.getTransaction().begin();
		T resultado = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		
		return resultado;
		
	}
	
	@Override
	public void delete(T entity) {

		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
		
	}

	@Override
	public T byId(long id) {

		entityManager.getTransaction().begin();
		T resultado = entityManager.find(entityClass, id);
		entityManager.getTransaction().commit();
		
		return resultado;
		
	}
	
	@Override
	public List<T> readAll() {

		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<T> resultados = entityManager.createQuery( "from " + entityClass.getName() ).getResultList();
		entityManager.getTransaction().commit();
		
		return resultados;
		
	}	

	@Override
	public boolean hasData() {

		return entityManager.createNativeQuery(String.format("SELECT EXISTS (SELECT 1 FROM %s)", getTableName())).getFirstResult() > 0;
		
	}
	
	@Override
	public Long cantidadElementos() { // TODO: no anda esto
		
//		return entityManager.createQuery(String.format("SELECT COUNT(a) FROM %s a", getTableName()), Long.class).getSingleResult();
//		return (Long) entityManager.createNativeQuery(String.format("SELECT COUNT(a) FROM %s a", getTableName()), Long.class).getSingleResult();
		return null;
		
	}
	
	private String getTableName() {
		
		Table table = entityClass.getAnnotation(Table.class);
		return table != null ? table.name() : entityClass.getSimpleName();
		
	}
	
//	public long cantidadElementos() {
//		return entityManager.createQuery(String.format("SELECT COUNT(a) FROM Stock a", entityClass.getSimpleName()), Long.class).getSingleResult();
//	}
	

	@Override
	public void cerrar() {

		entityManager.close();
		
	}

}
