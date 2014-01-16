package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;

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
		
		EmpresaService empresaService = ctx.getBean("springJpaEmpresaService", EmpresaService.class);

		// buscar empresa por id. si existe eliminar sino existe agregar
		Empresa empresa = empresaService.findByNif("1");
		System.out.println("");
		System.out.println("buscar por id 1:" + empresa);
		System.out.println("");
		
		System.out.println("eliminar");
		if (empresa != null)
		{
			empresaService.delete(empresa);
		}
		
		empresa = empresaService.findByNif("1");
		if (empresa != null)
		{
			fail("El empresa no se ha eliminado");
		}
		
		System.out.println("insertar");
		
		Set<Empleado> empleados = new HashSet<Empleado>();
		
		System.out.println("Guardar empresa");
		empresa = new Empresa("1", "Empresa1", "direccionFiscalEmpresa1",
				new Date(),empleados);
		
		empresaService.save(empresa);
		
		empresa = empresaService.findByNif("2");
		if (empresa == null)
		{
			fail("El empresa no se ha guardado");
		}
		
		//asertrue(empresa != null);
	}

}
