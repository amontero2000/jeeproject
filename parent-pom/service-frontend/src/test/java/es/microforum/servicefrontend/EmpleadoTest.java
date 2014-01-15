/**
 * 
 */
package es.microforum.servicefrontend;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import es.microforum.model.Empresa;

/**
 * @author Alberto
 *
 */
public class EmpleadoTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:spring-data-app-context.xml");
		ctx.refresh();
		
		System.out.println("Aplicacion de contexto inicializado correctamente");
		
		EmpleadoService empleadoService = ctx.getBean("springJpaEmpleadoService", EmpleadoService.class);

		// buscar empleado por id. si existe eliminar sino existe agregar
		Empleado empleado = empleadoService.findById("1");
		System.out.println("");
		System.out.println("buscar por id 1:" + empleado);
		System.out.println("");
		
		System.out.println("eliminar");
		if (empleado != null)
		{
			empleado.delete(empleado);
		}
		
		empleado = empleadoService.findById("1");
		if (empleado != null)
		{
			fail("El empleado no se ha eliminado");
		}
		
		System.out.println("insertar");
		
		Empresa empresa = new Empresa();
		byte[] imagen = new byte{2,3};
		
		System.out.println("Guardar empleado");
		empleado = new Empleado("2", empresa, "Empleado 2",
				"direccion 2", "tipo Empleado2", "empleado col2",
				4000, 33, 40, imagen);
		
		empleadoService.save(empleado);
		
		empleado = empleadoService.findById("2");
		if (empleado == null)
		{
			fail("El empleado no se ha guardado");
		}
		
		asertrue(empleado != null);

	}

}
