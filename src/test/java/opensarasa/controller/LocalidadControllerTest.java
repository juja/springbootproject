package opensarasa.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import opensarasa.entity.Localidad;
import opensarasa.repository.LocalidadRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalidadControllerTest {

	private MockMvc mvc;
	@Autowired
	private WebApplicationContext context;
	@Autowired
	private LocalidadRepository localidadRepository;
	private Localidad localidadTest;

	@Before
	public void setUp() throws Exception {
		//mvc = MockMvcBuilders.standaloneSetup(new DefaultController()).build();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		
	}

	@Test
	public void list() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/localidad").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk());
	}
	
	@Test
	public void newLocalidad() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/localidad/new").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(xpath("//html/body/div/div/form/div[1]/label").string("Nombre:"))
				.andExpect(xpath("//*[@id='nombre']").exists())
				.andExpect(xpath("/html/body/div/div/form/div[2]/label").string("Poblacion:"))
				.andExpect(xpath("//*[@id='poblacion']").exists());
	}
	
	@Test
	public void saveLocalidad() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/localidad").accept(MediaType.TEXT_HTML)
				.param("nombre", "Ciudad Test")
				.param("poblacion", "70000"))
				.andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void showLocalidad() throws Exception {
		Localidad localidad = new Localidad();
		localidad.setNombre("Ciudad Test 999");
		localidad.setPoblacion(123123);
		
		localidadRepository.save(localidad);
		
		mvc.perform(MockMvcRequestBuilders.get("/localidad/"+localidad.getId().toString()).accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(xpath("/html/body/div/div/form/div[2]/div/p").string(localidad.getNombre()))
				.andExpect(xpath("/html/body/div/div/form/div[3]/div/p").string(localidad.getPoblacion().toString()));
	}
	
	@Test
	public void editLocalidad() throws Exception {
		Localidad localidad = new Localidad();
		localidad.setNombre("Ciudad Test 998");
		localidad.setPoblacion(1231444);
		
		localidadRepository.save(localidad);
		
		mvc.perform(MockMvcRequestBuilders.get("/localidad/edit/"+localidad.getId().toString()).accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(xpath("//*[@id='nombre']/@value").string(localidad.getNombre()))
				.andExpect(xpath("//*[@id='poblacion']/@value").string(localidad.getPoblacion().toString()));
	}
}
