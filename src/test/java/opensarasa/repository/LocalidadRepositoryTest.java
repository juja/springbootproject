package opensarasa.repository;

import opensarasa.entity.Localidad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalidadRepositoryTest {

    private LocalidadRepository localidadRepository;

    @Autowired
    public void setLocalidadRepository(LocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }

    @Test
    public void testSaveLocalidad(){
        //setup product
        Localidad localidad = new Localidad();
        localidad.setNombre("La Plata");
        localidad.setPoblacion(521936);
        
        //Guardo ciudad y verfico que se le asigne un ID
        assertNull(localidad.getId()); //null antes de guardar
        localidadRepository.save(localidad);
        assertNotNull(localidad.getId()); //not null después de guardar

        //fetch from DB
        Localidad fetchedLocalidad = localidadRepository.findOne(localidad.getId());

        //should not be null
        assertNotNull(fetchedLocalidad);

        //should equal
        assertEquals(localidad.getId(), fetchedLocalidad.getId());
        assertEquals(localidad.getNombre(), fetchedLocalidad.getNombre());

        //update description and save
        fetchedLocalidad.setNombre("Buenos Aires");
        localidadRepository.save(fetchedLocalidad);

        //get from DB, should be updated
        Localidad fetchedUpdatedLocalidad = localidadRepository.findOne(fetchedLocalidad.getId());
        assertEquals(fetchedLocalidad.getNombre(), fetchedUpdatedLocalidad.getNombre());

        //verify count of products in DB
        long productCount = localidadRepository.count();
        assertEquals(productCount, 1);

        //get all products, list should only have one
        Iterable<Localidad> localidades = localidadRepository.findAll();

        int count = 0;

        for(Localidad l : localidades){
            count++;
        }

        assertEquals(count, 1);
    }
}
