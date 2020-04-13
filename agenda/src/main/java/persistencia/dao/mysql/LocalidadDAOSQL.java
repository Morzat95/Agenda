package persistencia.dao.mysql;

import java.util.List;

import dto.LocalidadDTO;
import dto.ProvinciaDTO;
import persistencia.dao.interfaz.LocalidadDAO;

public class LocalidadDAOSQL extends DAOJPA<LocalidadDTO> implements LocalidadDAO {

	@Override
	public List<LocalidadDTO> readBy(ProvinciaDTO provincia) {
		
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<LocalidadDTO> resultados = entityManager.createQuery( "from " + LocalidadDTO.class.getName() + " l where l.provincia =:provincia").setParameter("provincia", provincia).getResultList();
		entityManager.getTransaction().commit();
		
		return resultados;
		
	}

}
