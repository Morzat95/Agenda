package persistencia.dao.interfaz;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagers {
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.agenda.jpa");
	
	private EntityManagers() {
		
	}
	
	public static EntityManager createEntityManagers() {
		
		return entityManagerFactory.createEntityManager();
		
	}

}
