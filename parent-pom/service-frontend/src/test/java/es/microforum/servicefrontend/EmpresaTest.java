package es.microforum.servicefrontend;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.context.support.GenericXmlApplicationContext;

public class EmpresaTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:spring-data-app-context.xml");
		ctx.refresh();
		
		System.out.println("Aplicacion de contexto inicializado correctamente");
		
		PersonaService personaService = ctx.getBean("springJpaEmpresaService", EmpresaService.class);

		// buscar todas personas
		List<Persona> personas = personaService.findAll();		
		listPersonas(personas);
		
		// buscar persona por nombre???
		System.out.println("buscar persona por nombre");
		personas = personaService.findByNombre((String) "nom3");
		listPersonas(personas);
		
		// buscar persona por nombre y salario
		System.out.println("buscar persona por nombre y salario");
		personas = personaService.findByNombreAndSalario("nom3", (long) 1000);		
		listPersonas(personas);	
		
		// Add new contact???
		System.out.println("Agregar nueva persona");
		Persona persona = new Persona();
		persona.setId(25);
		persona.setNombre("Prueba insert nombre");
		persona.setSalario((long) 100.22);
		persona.setFechaNacimiento((java.util.Date) new Date());
		personaService.save(persona);
		personas = personaService.findAll();
		listPersonas(personas);	
		
		// Find by id???
		persona = personaService.findById(1);
		System.out.println("");
		System.out.println("buscar por id 1:" + persona);
		System.out.println("");
		
		// Update persona
		System.out.println("guardar persona");
		persona.setNombre("Prueba update nombre");
		personaService.save(persona);
		personas = personaService.findAll();
		listPersonas(personas);
		
		fail("Not yet implemented");
	}

}
