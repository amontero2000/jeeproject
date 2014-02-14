package es.microforum.integrationtest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;


public class EmpleadoServiceRestTest {
	
	// Contexto de Spring
	private ClassPathXmlApplicationContext ctxSpring;
	// Data Source
	DataSource dataSource;
	// Jdbc Template
	private JdbcTemplate jdbcTemplate;
	// Empleado
	Empleado empleado;
	// Bean de servicio
	EmpleadoService empleadoService;
	// restTemplate
	RestTemplate restTemplate = new RestTemplate();
	// URI base:
	String uriBase;


	@Before
	public void before() throws Exception {
		// Cargar Application context
		ctxSpring = new ClassPathXmlApplicationContext("classpath:spring-data-app-context.xml");
			// Iniciar Data Source
		dataSource = (DataSource) ctxSpring.getBean("dataSource"); 
			// conexion JdbcTemplate
		jdbcTemplate = new JdbcTemplate(dataSource);
			// Crear nuevo Empleado
		empleado = new Empleado("dniJUnit", null, "empleadoJUnit", "Calle emp1", "tipoEmpleado1", "empleadoCol1", 180234.0, 143.5, 138.0, null);
			// Empleado service
		empleadoService = ctxSpring.getBean("springJpaEmpleadoService", EmpleadoService.class);
			// Definir URI de base para llamar a servicio de empleado
		uriBase = "http://localhost:8081/service-frontend/empleado/";
	}


	@Test
	public void deleteEmpleadoTest() {
		try {
			jdbcTemplate.execute("INSERT INTO `JEE`.`empleado` (`dni`, `nombre`, `direccion`, `tipoEmpleado`, `empleadocol`, `salarioAnual`, `valorHora`, `cantidadHoras`, `nif`, `version`) VALUES ('dni1', 'Empleado1 Rest Api', 'Calle empleado 1', 'tipoEmpleado1', 'empleadoCol1', '1234', '234', '1234', '2', '0');");
			assertTrue(empleadoService.buscarPorDni("dni1") != null);
			// Eliminar empleado llamando al web service rest api
			restTemplate.delete(uriBase + "dni1");
			assertTrue(empleadoService.buscarPorDni("dni1") == null);
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
		    String body = "{\"dni\":\"dni1\",\"nombre\":\"Empleado Rest Api\",\"direccion\":\"Calle Rest, Api 45\",\"tipoEmpleado\":\"tipoEmpleadoRest\"},\"empleadoCol\":\"empleadoColRest\"},\"salarioAnual\":\"18000\"},\"valorHora\":\"18\"},\"cantidadHoras\":\"25\"},\"nif\":\"2\"}}"; 
	        HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
	        ResponseEntity<String> response = restTemplate.exchange(uriBase, post, entity, String.class); 
	        // comprobamos si el estado de la petición es correcto
	        assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
	        // comprobamos si se ha insertado el registro en bbdd
	        assertTrue(empleadoService.buscarPorDni("dni1") != null);
	        // Eliminar empresa
	        restTemplate.delete(uriBase + "dni1");
	        assertTrue(empleadoService.buscarPorDni("dni1") == null);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}


	@Test
	public void putEmpleadoTest() {
		try {
			// Intertar empleado 
			jdbcTemplate.execute("INSERT INTO `JEE`.`empleado` (`dni`, `nombre`, `direccion`, `tipoEmpleado`, `empleadocol`, `salarioAnual`, `valorHora`, `cantidadHoras`, `nif`, `version`) VALUES ('dni1', 'Empleado1', 'Calle empleado1', 'tipoEmpleado1', 'empleadoCol1', '15123', '3125', '2130', '2', '0');");
			// URL del objeto creado
			String url = uriBase + "/dni1";
			// Parámetros de la petición 
			String acceptHeaderValue = "application/json"; 
			HttpHeaders requestHeaders = new HttpHeaders(); 
	        List<MediaType> mediaTypes = new ArrayList<MediaType>(); 
	        mediaTypes.add(MediaType.valueOf(acceptHeaderValue)); 
	        requestHeaders.setAccept(mediaTypes); 
	        requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue)); 
	        HttpMethod put = HttpMethod.PUT; 
		    // En el body pasamos el JSON para la modificación de un empleado
		    String body = "{\"nombre\":\"Empleado1 MODIFICADO\"}}"; 
	        HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
	        ResponseEntity<String> response = restTemplate.exchange(url, put, entity, String.class); 
	        // Comprobar si el estado de la petición sea correcto
	        assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
	        // Comprobar si el registro se haya modificado en BBDD
	        assertTrue(empleadoService.buscarPorDni("dni1").getNombre().equals("Empleado1 MODIFICADO"));
	        // Eliminar empleado creado
	        restTemplate.delete(uriBase + "dni1");
	        // Compruebo que no queden registros en la BBDD
	        assertTrue(empleadoService.buscarPorDni("dni1") == null);
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
