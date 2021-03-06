/**
 * 
 */
package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceapi.EmpresaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Alberto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = "classpath:spring-data-app-context.xml")

public class EmpleadoTest {
	@Autowired 
	private ApplicationContext ctx;
	EmpleadoService empleadoService;
	EmpresaService empresaService;
	Empleado empleado;
	private static Logger logger;
	

    @Before
    public void setUp() throws Exception {
    	empleadoService = ctx.getBean("springJpaEmpleadoService", EmpleadoService.class);
    	empresaService = ctx.getBean("springJpaEmpresaService", EmpresaService.class);
    	logger = LoggerFactory.getLogger(EmpleadoTest.class);
    }

	@Test
	@Transactional
	public void testinsertarEmpleado() {
		logger.info("- Insertar Empleado");
		
		Empresa empresa = empresaService.buscarPorNif("1");
		
		empleado = empleadoService.buscarPorDni("1");
		if (empleado == null)
		{
			byte[] imagen = null;
			empleado = new Empleado("1", empresa, "Empleado 1","direccion 1", "tipo Empleado1", "empleado col1",70.55, 43.44, 48.75,imagen);			
		}
		else
		{
			empleado.setEmpresa(empresa);
		}
		empleadoService.guardar(empleado);
		empleado = empleadoService.buscarPorDni("1");
		
		if(empleado==null)
		{
			logger.error("los datos de empleado no se han guardado en la bbdd");
		}
		
		assertTrue(empleado != null);
	}
	
	@Test
	@Transactional
	public void testModificarEmpleado() {
		logger.info("- Modificar Empleado sino existe insertamos uno");
		
		empleado = empleadoService.buscarPorDni("1");
		if (empleado == null)
		{
			testinsertarEmpleado();
		}
		else
		{
			empleado.setNombre("empleado1 modificado");
			empleadoService.guardar(empleado);
		}
		
		empleado = empleadoService.buscarPorDni("1");
		assertTrue(empleado.getNombre().equals("empleado1 modificado"));
	}
	
	@Test
	@Transactional
	public void buscarEmpleado() {
		logger.info("- buscar Empleado sino existe insertamos uno previamente");
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
		logger.info("- eliminar Empleado sino existe insertamos uno previamente");
		empleado = empleadoService.buscarPorDni("1");
		if (empleado == null)
		{
			testinsertarEmpleado();
		}
		
		empleadoService.eliminar(empleado);
		
		if(empleadoService.buscarPorDni("1") != null)
		{
			logger.error("No se ha podido eliminar empleado de bbdd");
		}
		
		assertTrue(empleadoService.buscarPorDni("1") == null);
	}
	
	@Test
	@Transactional
	public void buscarEmpleados() {
		logger.info("- buscar todos los empleados sino existe ninguno insertamos uno");
		List<Empleado> empleados = empleadoService.buscarEmpleados();
		
		if (empleados.size() == 0)
		{
			testinsertarEmpleado();
			empleados = empleadoService.buscarEmpleados();
		}
		assertTrue(empleados.size() > 0);
	}

}
