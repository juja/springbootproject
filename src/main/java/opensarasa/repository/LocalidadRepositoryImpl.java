package opensarasa.repository;

import java.util.List;
import opensarasa.entity.Localidad;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

public class LocalidadRepositoryImpl implements LocalidadRepositoryCustom {

	@Autowired
	EntityManager entityManager;
	
	
	public List<Localidad> findByTipo(Localidad.Tipo tipo) {
		Query query;
		String condicion = "";
		
		switch(tipo){
			case PUEBLO:
				condicion = "l.poblacion < 5000";
				break;
			case CIUDAD:
				condicion = "l.poblacion >= 5000";
				break;
		}
		query = entityManager.createQuery("SELECT l FROM Localidad l WHERE " + condicion);
		return query.getResultList();
	}

}
