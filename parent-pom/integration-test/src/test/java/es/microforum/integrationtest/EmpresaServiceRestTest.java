package es.microforum.integrationtest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.text.SimpleDateFormat;
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

import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;


public class EmpresaServiceRestTest {
	
	    //Contexto de Spring
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
			empresa = new Empresa("nif1", "empresa1RestApi", "Calle rest 8, 3B", sdf.parse("01/01/2014"), null );
			// Empresa service
			empresaService = ctxSpring.getBean("springJpaEmpresaService", EmpresaService.class);
			// Definir URI de base para llegar a todas las empresas
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
				// Insertar empresa sino existe
				//if(empresaService.buscarPorNif("nif1") == null)
				//{
					jdbcTemplate.execute("INSERT INTO `JEE`.`empresa` (`nif`, `nombre`, `direccionFiscal`, `fechaInicioActividades`, `version`) VALUES ('nif1', 'Empresa 1', 'Calle empresa1', '2014-01-01 00:00:00', '0');");
				//}
				// Eliminar empresa llamando al servicio de res api
				restTemplate.delete(uriBase + "nif1");
				assertTrue(empresaService.buscarPorNif("nif1") == null);
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
			    // En el body pasamos el JSON para crear empresa
			    String body = "{\"nif\":\"nif1\",\"nombre\":\"Empresa 1\",\"direccionFiscal\":\"Calle emp1, Api 45\",\"fechaInicioActividades\":\"2011-11-17\"}}"; 
		        HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
		        ResponseEntity<String> response = restTemplate.exchange(uriBase, post, entity, String.class); 
		        // Comprobar si el estado de la peticion es correcto
		        assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
		        // comprobar si se ha insertado en bbdd
		        assertTrue(empresaService.buscarPorNif("nif1") != null);
		        // eliminar empresa
		        restTemplate.delete(uriBase + "nif1");
		        // Comprobamos si se ha eliminado de bbdd
		        assertTrue(empresaService.buscarPorNif("nif1") == null);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}


		@Test
		public void putEmpresaTest() {
			try {
				jdbcTemplate.execute("INSERT INTO `JEE`.`empresa` (`nif`, `nombre`, `direccionFiscal`, `fechaInicioActividades`, `version`) VALUES ('nif1', 'Empresa 3', 'Calle 3, 3A', '2014-01-01 00:00:00', '0');");
				// URL del objeto creado
				String url = uriBase + "/nif1";
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
		        // Comprobamos si el estado de la peticion es correcto
		        assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
		        // Comompobamos si se ha modificado en bbdd
		        assertTrue(empresaService.buscarPorNif("nif1").getNombre().equals("Empresa MODIFICADA Rest Api"));
		        // Eliminar empresa
		        restTemplate.delete(uriBase + "nif1");
		        // Comprobar si se ha eliminado
		        assertTrue(empresaService.buscarPorNif("nif1") == null);
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}

}
