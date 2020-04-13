package persistencia.dao.mysql;

import java.util.List;

import dto.PaísDTO;
import dto.ProvinciaDTO;
import persistencia.dao.interfaz.ProvinciaDAO;

public class ProvinciaDAOSQL extends DAOJPA<ProvinciaDTO> implements ProvinciaDAO {

	@Override
	public List<ProvinciaDTO> readBy(PaísDTO país) {
		
		entityManager.getTransaction().begin();
		@SuppressWarnings("unchecked")
//		List<ProvinciaDTO> resultados = entityManager.createQuery( "from " + ProvinciaDTO.class.getName() + " where país_id=:país").setParameter("país", país.getIdPaís()).getResultList();
		List<ProvinciaDTO> resultados = entityManager.createQuery( "from " + ProvinciaDTO.class.getName() + " p where p.país=:país").setParameter("país", país).getResultList();
		entityManager.getTransaction().commit();
		
		return resultados;
		
	}

}
