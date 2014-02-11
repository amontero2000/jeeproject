//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.hateoas.Resource;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//
//import com.mapfre.dgtp.rest.model.SubtipoSerie;
//import com.mapfre.dgtp.rest.repository.TipoSerieRepository;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//// ApplicationContext will be loaded from
//@ContextConfiguration(locations = { "classpath:amd.xml" })
//@ActiveProfiles("integration_test")
//public class SubtipoSerieRepositoryJsonClientTest {
//
//	@Autowired
//	String jpaWebContext;
//	@Autowired
//	ApplicationContext context;
//
//	@Autowired
//	TipoSerieRepository tipoSerieRepository;
//
//	private JdbcTemplate jdbcTemplate;
//
//	@Before
//	public void before() {
//		DataSource dataSource = (DataSource) context.getBean("dataSource");
//		jdbcTemplate = new JdbcTemplate(dataSource);
//		jdbcTemplate.execute("DELETE FROM subtipo_serie where cod_subtipo_ser=9999");
//	}
//
//	@Autowired
//	RestTemplate restTemplate;
//
//	@Test
//	public void getTest() {
//		try {
//			Map<String, String> vars = new HashMap<String, String>();
//			vars.put("codSubtipoSer", "0");
//			Resource<SubtipoSerie> resource = getSubtipoSerie(new URI(jpaWebContext + "subtipoSeries/0"));
//			assertTrue(resource.getContent().getDesSubtipoSer().equals("Todas"));
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
//
//	private Resource<SubtipoSerie> getSubtipoSerie(URI uri) {
//		return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<SubtipoSerie>>() {
//		}).getBody();
//
//	}
//
//	@Test
//	public void deleteTest() {
//		try {
//			jdbcTemplate.execute("INSERT INTO subtipo_serie values(9999,1,'BORRAR','BORRAR','test',SYSDATE)");
//			int count = jdbcTemplate.queryForInt("select count(*) from subtipo_serie where cod_subtipo_ser=9999");
//			assertTrue(count == 1);
//			restTemplate.delete(jpaWebContext + "subtipoSeries/9999");
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//		int count = jdbcTemplate.queryForInt("select count(*) from subtipo_serie where cod_subtipo_ser=9999");
//		assertTrue(count == 0);
//	}
//
//	@Test
//	public void postTest() throws RestClientException, URISyntaxException {
//		jdbcTemplate.execute("DELETE FROM subtipo_serie where nom_subtipo_ser like 'BORRAR!%'");
//		String url = jpaWebContext + "subtipoSeries";
//		String acceptHeaderValue = "application/json";
//
//		HttpHeaders requestHeaders = new HttpHeaders();
//		List<MediaType> mediaTypes = new ArrayList<MediaType>();
//		mediaTypes.add(MediaType.valueOf(acceptHeaderValue));
//		requestHeaders.setAccept(mediaTypes);
//		requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue));
//		HttpMethod post = HttpMethod.POST;
//
//		String body = "{\"codUsr\":\"test\",\"nomSubtipoSer\":\"BORRAR!\",\"fecActu\":\"2011-11-17\",\"desSubtipoSer\":\"BORRAR!\",\"tipoSerie\":{\"codTipoSerie\":\"1\"}}";
//		HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
//
//		ResponseEntity<String> response = restTemplate.exchange(url, post, entity, String.class);
//		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
//		int count = jdbcTemplate.queryForInt("select count(*) from subtipo_serie where nom_subtipo_ser like 'BORRAR!%'");
//		assertTrue(count == 1);
//		jdbcTemplate.execute("DELETE FROM subtipo_serie where nom_subtipo_ser like 'BORRAR!%'");
//	}
//
//	@Test
//	public void putTest() throws RestClientException, URISyntaxException {
//		jdbcTemplate.execute("INSERT INTO subtipo_serie values(9999,1,'BORRAR','BORRAR','test',SYSDATE)");
//		String url = jpaWebContext + "subtipoSeries/9999";
//		String acceptHeaderValue = "application/json";
//
//		HttpHeaders requestHeaders = new HttpHeaders();
//		List<MediaType> mediaTypes = new ArrayList<MediaType>();
//		mediaTypes.add(MediaType.valueOf(acceptHeaderValue));
//		requestHeaders.setAccept(mediaTypes);
//		requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue));
//		HttpMethod put = HttpMethod.PUT;
//
//		String body = "{\"nomSubtipoSer\":\"MODIFICADO!\"}";
//		HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);
//
//		ResponseEntity<String> response = restTemplate.exchange(url, put, entity, String.class);
//		assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
//		int count = jdbcTemplate.queryForInt("select count(*) from subtipo_serie where nom_subtipo_ser = 'MODIFICADO!'");
//		assertTrue(count == 1);
//		jdbcTemplate.execute("DELETE FROM subtipo_serie where nom_subtipo_ser = 'MODIFICADO!'");
//	}
//
//	@After
//	public void after() {
//		jdbcTemplate.execute("DELETE FROM subtipo_serie where cod_subtipo_ser=9999");
//	}
//
//}
