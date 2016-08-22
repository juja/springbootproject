package opensarasa.repository;

import opensarasa.entity.Localidad;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

interface LocalidadRepositoryCustom{	
	List<Localidad> findByTipo(Localidad.Tipo tipo);	
}

public interface LocalidadRepository extends CrudRepository<Localidad, Integer>, LocalidadRepositoryCustom{
	List<Localidad> findByNombre(String nombre);
}


