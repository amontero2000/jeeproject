package es.microforum.integrationtest;

import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;


import static org.junit.Assert.fail;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceapi.EmpresaService;


public class EmpleadoServiceRestTest {
	
	// Contexto de Spring
	private ClassPathXmlApplicationContext ctxSpring;
	// Data Source
	DataSource dataSource;
	// Jdbc Template
	private JdbcTemplate jdbcTemplate;
	// Empresa
	Empleado empleado;
	// Bean de servicio
	EmpleadoService empleadoService;
	// restTemplate
	RestTemplate restTemplate = new RestTemplate();
	// URI base:
	String uriBase;


	@Before
	public void before() throws Exception {
		// Application context
		ctxSpring = new ClassPathXmlApplicationContext("classpath:spring-data-app-context.xml");
			// Data Source
		dataSource = (DataSource) ctxSpring.getBean("dataSource"); 
			// JdbcTemplate
		jdbcTemplate = new JdbcTemplate(dataSource);
			// Empleado
		empleado = new Empleado("dniJUnit", null, "empleadoJUnit", "Calle 1, 1A", "tipoEmpleado1", "empleadoCol1", 18000.0, 12.5, 148.0, null);
			// Empresa service
		empleadoService = ctxSpring.getBean("springJpaEmpleadoService", EmpleadoService.class);
			// Defino la URI de base para llegar a todas las empresas
		uriBase = "http://localhost:8081/service-frontend/empleado/";
	}


	@Test
	public void deleteEmpleadoTest() {
		try {
			// Inserto un empleado para su posterior borrado
			jdbcTemplate.execute("INSERT INTO `JEE`.`empleado` (`dni`, `nombre`, `direccion`, `tipoEmpleado`, `empleadocol`, `salarioAnual`, `valorHora`, `cantidadHoras`, `nif`, `version`) VALUES ('dniRestApi', 'Empleado Rest Api', 'Calle Rest, api 10', 'tipoEmpleadoRest', 'empleadoColRest', '15000', '15', '120', '2', '0');");
			assertTrue(empleadoService.buscarPorDni("dniRestApi") != null);
			// Elimino la empresa mediante rest api
			restTemplate.delete(uriBase + "dniRestApi");
			assertTrue(empleadoService.buscarPorDni("dniRestApi") == null);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	@Test
	public void postEmpleadoTest() {
		try {
			// Parámetros de la petición 
			String acceptHeaderValue = "application/json"; 
			HttpHeaders requestHeaders = new HttpHeaders(); 
		    List<MediaType> mediaTypes = new ArrayList<MediaType>(); 
		    mediaTypes.add(MediaType.valueOf(acceptHeaderValue)); 
		    requestHeaders.setAccept(mediaTypes); 
		    requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue)); 
		    HttpMethod post = HttpMethod.POST;
		    // En el body pasamos el JSON para la creación de un empleado
		    String body = "{\"dni\":\"dniRestApi\",\"nombre\":\"Empleado Rest Api\",\"direccion\":\"Calle Rest, Api 45\",\"tipoEmpleado\":\"tipoEmpleadoRest\"},\"empleadoCol\":\"empleadoColRest\"},\"salarioAnual\":\"18000\"},\"valorHora\":\"18\"},\"cantidadHoras\":\"25\"},\"nif\":\"2\"}}"; 
	        HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
	        ResponseEntity<String> response = restTemplate.exchange(uriBase, post, entity, String.class); 
	        // Compruebo que el estado de la petición sea correcto
	        assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
	        // Compruebo que el registro se haya insertado en BBDD
	        assertTrue(empleadoService.buscarPorDni("dniRestApi") != null);
	        // Elimino la empresa creada
	        restTemplate.delete(uriBase + "dniRestApi");
	        // Compruebo que no queden registros en la BBDD
	        assertTrue(empleadoService.buscarPorDni("dniRestApi") == null);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	@Test
	public void putEmpleadoTest() {
		try {
			// Inserto un empleado para su posterior modificación
			jdbcTemplate.execute("INSERT INTO `JEE`.`empleado` (`dni`, `nombre`, `direccion`, `tipoEmpleado`, `empleadocol`, `salarioAnual`, `valorHora`, `cantidadHoras`, `nif`, `version`) VALUES ('dniRestApi', 'Empleado Rest Api', 'Calle Rest, api 10', 'tipoEmpleadoRest', 'empleadoColRest', '15000', '15', '120', '2', '0');");
			// URL del objeto creado
			String url = uriBase + "/dniRestApi";
			// Parámetros de la petición 
			String acceptHeaderValue = "application/json"; 
			HttpHeaders requestHeaders = new HttpHeaders(); 
	        List<MediaType> mediaTypes = new ArrayList<MediaType>(); 
	        mediaTypes.add(MediaType.valueOf(acceptHeaderValue)); 
	        requestHeaders.setAccept(mediaTypes); 
	        requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue)); 
	        HttpMethod put = HttpMethod.PUT; 
		    // En el body pasamos el JSON para la modificación de un empleado
		    String body = "{\"nombre\":\"Empleado MODIFICADO Rest Api\"}}"; 
	        HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
	        ResponseEntity<String> response = restTemplate.exchange(url, put, entity, String.class); 
	        // Compruebo que el estado de la petición sea correcto
	        assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
	        // Compruebo que el registro se haya modificado en BBDD
	        assertTrue(empleadoService.buscarPorDni("dniRestApi").getNombre().equals("Empleado MODIFICADO Rest Api"));
	        // Elimino el empleado creado
	        restTemplate.delete(uriBase + "dniRestApi");
	        // Compruebo que no queden registros en la BBDD
	        assertTrue(empleadoService.buscarPorDni("dniRestApi") == null);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	
	@Test
	public void buscarEmpleado() {
		try
		{
			Resource<Empleado> resource = getEmpleado(new URI("http://localhost:8081/service-frontend/empleado/1"));
			assertTrue(resource.getContent().getNombre().equals("empleado1 modificado"));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	private Resource<Empleado> getEmpleado(URI uri) {
		return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Empleado>>() {
		}).getBody();

	}
	
	

}
