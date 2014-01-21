package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceapi.EmpresaService;


@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback=true)
@ContextConfiguration(locations = "classpath:spring-data-app-context.xml")
public class EmpresaTest {
	@Autowired 
	private ApplicationContext ctx;
	EmpresaService empresaService;
	Empresa empresa;
	Empresa empresanueva;
	SimpleDateFormat sdf;
	Set <Empleado> empleados;


    @Before
    public void setUp() throws Exception {
    	empresaService = ctx.getBean("springJpaEmpresaService", EmpresaService.class);
    	sdf = new SimpleDateFormat("dd/MM/yyyy");
    	empleados = new HashSet<Empleado>();
    	empresanueva = new Empresa("1", "Empresa1", "direccionFiscalEmpresa1",sdf.parse("05/07/2013"),empleados);
    }

	@Test
	//@Transactional
	public void testinsertarEmpresa() {
		System.out.println("- Insertar Empresa");
		
		empresaService.guardar(empresanueva);
		
		empresa = empresaService.buscarPorNif("1");
		assertTrue(empresa != null);
	}
	
	@Test
	@Transactional
	public void testModificarEmpresa() {
		System.out.println("- Modificar Empresa sino existe insertamos una previamente");
		
		empresa = empresaService.buscarPorNif("1");
		if (empresa == null)
		{
			testinsertarEmpresa();
		}
		
		empresa.setNombre("nombreModificado");
		empresaService.guardar(empresa);
		
		empresa = empresaService.buscarPorNif("1");
		assertTrue(empresa.getNombre().equals("nombreModificado"));
	}
	
	@Test
	@Transactional
	public void buscarEmpresa() {
		System.out.println("- buscar Empresa sino existe insertamos una previamente");
		empresa = empresaService.buscarPorNif("1");
		if (empresa == null)
		{
			testinsertarEmpresa();
		}
		
		empresa = empresaService.buscarPorNif("1");
		assertTrue(empresa != null);
	}
	
	@Test
	@Transactional
	public void eliminarEmpresa() {
		System.out.println("- eliminar Empresa sino existe insertamos una previamente");
		empresa = empresaService.buscarPorNif("1");
		if (empresa == null)
		{
			testinsertarEmpresa();
		}
		
		empresaService.eliminar(empresa);
		assertTrue(empresaService.buscarPorNif("1") == null);
	}
	
	@Test
	@Transactional
	public void buscarTodasEmpresas() {
		System.out.println("- buscar todas las empresas sino existe ninguna insertamos una");
		List<Empresa> empresas = empresaService.buscarEmpresas();
		
		if (empresas.size() == 0)
		{
			testinsertarEmpresa();
			empresas = empresaService.buscarEmpresas();
		}
		assertTrue(empresas.size() > 0);
	}

}
