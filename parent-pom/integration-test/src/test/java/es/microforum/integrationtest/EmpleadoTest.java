/**
 * 
 */
package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;


/**
 * @author Alberto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=false)
@ContextConfiguration(locations = "classpath:spring-data-app-context.xml")

public class EmpleadoTest {
	@Autowired 
	private ApplicationContext ctx;
	EmpleadoService empleadoService;
	Empleado empleado;


    @Before
    public void setUp() throws Exception {
    	empleadoService = ctx.getBean("springJpaEmpleadoService", EmpleadoService.class);
    }

	@Test
	@Transactional
	public void testinsertarEmpleado() {
		System.out.println("- Insertar Empleado");
		
		byte[] imagen = null;
		
		/*File file = new File("C:\\_jee64\\wsproject\\code.gif");
		if (file.exists())
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        FileInputStream fis = new FileInputStream(file);
	        byte[] buf = new byte[1024];
	        for (int readNum; (readNum = fis.read(buf)) != -1;) {
	        	bos.write(buf, 0, readNum);   
	        }
	        imagen = bos.toByteArray();
		}*/
        
		empleado = new Empleado("1", null, "Empleado 1","direccion 1", "tipo Empleado1", "empleado col1",4000.55, 33.88, 40.55,imagen);
		empleadoService.guardar(empleado);
		
		empleado = empleadoService.buscarPorDni("1");
		assertTrue(empleado != null);
	}
	
	@Test
	@Transactional
	public void testModificarEmpleado() {
		System.out.println("- Modificar Empleado sino existe insertamos uno previamente");
		
		empleado = empleadoService.buscarPorDni("1");
		if (empleado == null)
		{
			testinsertarEmpleado();
		}
		
		empleado.setNombre("nombreModificado");
		empleadoService.guardar(empleado);
		
		empleado = empleadoService.buscarPorDni("1");
		assertTrue(empleado.getNombre().equals("nombreModificado"));
	}
	
	@Test
	@Transactional
	public void buscarEmpleado() {
		System.out.println("- buscar Empleado sino existe insertamos uno previamente");
		empleado = empleadoService.buscarPorDni("1");
		if (empleado == null)
		{
			testinsertarEmpleado();
		}
		
		empleado = empleadoService.buscarPorDni("1");
		assertTrue(empleado != null);
	}
	
	@Test
	@Transactional
	public void eliminarEmpleado() {
		System.out.println("- eliminar Empleado sino existe insertamos uno previamente");
		empleado = empleadoService.buscarPorDni("1");
		if (empleado == null)
		{
			testinsertarEmpleado();
		}
		
		empleadoService.eliminar(empleado);
		assertTrue(empleadoService.buscarPorDni("1") == null);
	}
	
	@Test
	@Transactional
	public void buscarEmpleados() {
		System.out.println("- buscar todos los empleados sino existe ninguno insertamos uno");
		List<Empleado> empleados = empleadoService.buscarEmpleados();
		
		if (empleados.size() == 0)
		{
			testinsertarEmpleado();
			empleados = empleadoService.buscarEmpleados();
		}
		assertTrue(empleados.size() > 0);
	}

}
