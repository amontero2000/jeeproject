package es.microforum.integrationtest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
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
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceapi.EmpresaService;


public class EmpresaServiceRestTest {
	
	// Contexto de Spring
		private ClassPathXmlApplicationContext ctxSpring;
		// Data Source
		DataSource dataSource;
		// Jdbc Template
		private JdbcTemplate jdbcTemplate;
		// Empresa
		Empresa empresa;
		// Bean de servicio
		EmpresaService empresaService;
		// restTemplate
		RestTemplate restTemplate = new RestTemplate();
		// Formateo de fechas
		SimpleDateFormat sdf;
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
			// SimpleDateFormat
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			// Empresa
			empresa = new Empresa("nifRestApi", "empresaRestApi", "Calle rest 8, 3B", sdf.parse("01/01/2014"), null );
			// Empresa service
			empresaService = ctxSpring.getBean("springJpaEmpresaService", EmpresaService.class);
			// Defino la URI de base para llegar a todas las empresas
			uriBase = "http://localhost:8081/service-frontend/empresa/";
		}


		@Test
		public void buscarEmpresa() {
			try {
				Resource<Empresa> resource = getEmpresa(new URI("http://localhost:8081/service-frontend/empresa/1"));
				assertTrue(resource.getContent().getNombre().equals("empresa1"));
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
		
		private Resource<Empresa> getEmpresa(URI uri) {
			return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Empresa>>() {
			}).getBody();
		}
		
		@Test
		public void deleteEmpresaTest() {
			try {
				// Inserto una empresa para su posterior borrado
				jdbcTemplate.execute("INSERT INTO `JEE`.`empresa` (`nif`, `nombre`, `direccionFiscal`, `fechaInicioActividades`, `version`) VALUES ('nifRestApi', 'Empresa 3', 'Calle 3, 3A', '2014-01-01 00:00:00', '0');");
				assertTrue(empresaService.buscarPorNif("nifRestApi") != null);
				// Elimino la empresa mediante rest api
				restTemplate.delete(uriBase + "nifRestApi");
				assertTrue(empresaService.buscarPorNif("nifRestApi") == null);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}


		@Test
		public void postEmpresaTest() {
			try {
				// Parámetros de la petición 
				String acceptHeaderValue = "application/json"; 
				HttpHeaders requestHeaders = new HttpHeaders(); 
			    List<MediaType> mediaTypes = new ArrayList<MediaType>(); 
			    mediaTypes.add(MediaType.valueOf(acceptHeaderValue)); 
			    requestHeaders.setAccept(mediaTypes); 
			    requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue)); 
			    HttpMethod post = HttpMethod.POST;
			    // En el body pasamos el JSON para la creación de una empresa
			    String body = "{\"nif\":\"nifRestApi\",\"nombre\":\"Empresa Rest Api\",\"direccionFiscal\":\"Calle Rest, Api 45\",\"fechaInicioActividades\":\"2011-11-17\"}}"; 
		        HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
		        ResponseEntity<String> response = restTemplate.exchange(uriBase, post, entity, String.class); 
		        // Compruebo que el estado de la petición sea correcto
		        assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
		        // Compruebo que el registro se haya insertado en BBDD
		        assertTrue(empresaService.buscarPorNif("nifRestApi") != null);
		        // Elimino la empresa creada
		        restTemplate.delete(uriBase + "nifRestApi");
		        // Compruebo que no queden registros en la BBDD
		        assertTrue(empresaService.buscarPorNif("nifRestApi") == null);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}


		@Test
		public void putEmpresaTest() {
			try {
				// Inserto una empresa para su posterior modificación
				jdbcTemplate.execute("INSERT INTO `JEE`.`empresa` (`nif`, `nombre`, `direccionFiscal`, `fechaInicioActividades`, `version`) VALUES ('nifRestApi', 'Empresa 3', 'Calle 3, 3A', '2014-01-01 00:00:00', '0');");
				// URL del objeto creado
				String url = uriBase + "/nifRestApi";
				// Parámetros de la petición 
				String acceptHeaderValue = "application/json"; 
				HttpHeaders requestHeaders = new HttpHeaders(); 
		        List<MediaType> mediaTypes = new ArrayList<MediaType>(); 
		        mediaTypes.add(MediaType.valueOf(acceptHeaderValue)); 
		        requestHeaders.setAccept(mediaTypes); 
		        requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue)); 
		        HttpMethod put = HttpMethod.PUT; 
			    // En el body pasamos el JSON para la modificación de una empresa
			    String body = "{\"nombre\":\"Empresa MODIFICADA Rest Api\"}}"; 
		        HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
		        ResponseEntity<String> response = restTemplate.exchange(url, put, entity, String.class); 
		        // Compruebo que el estado de la petición sea correcto
		        assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
		        // Compruebo que el registro se haya modificado en BBDD
		        assertTrue(empresaService.buscarPorNif("nifRestApi").getNombre().equals("Empresa MODIFICADA Rest Api"));
		        // Elimino la empresa creada
		        restTemplate.delete(uriBase + "nifRestApi");
		        // Compruebo que no queden registros en la BBDD
		        assertTrue(empresaService.buscarPorNif("nifRestApi") == null);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}

}
