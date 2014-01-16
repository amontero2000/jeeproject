/**
 * 
 */
package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceimpl.EmpleadoServiceImp;

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
		Empleado empleado = empleadoService.findByDni("1");
		System.out.println("");
		System.out.println("buscar por id 1:" + empleado);
		System.out.println("");
		
		System.out.println("eliminar");
		if (empleado != null)
		{
			empleadoService.delete(empleado);
		}
		
		empleado = empleadoService.findByDni("1");
		if (empleado != null)
		{
			fail("El empleado no se ha eliminado");
		}
		
		System.out.println("insertar");
		
		Empresa empresa = new Empresa();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try {
			
		File file = new File("C:\\_jee64\\wsproject\\code.gif");
        FileInputStream fis = new FileInputStream(file);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.
       
        byte[] buf = new byte[1024];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);   
            }
		 } catch (IOException ex) {
	        	System.out.println("Error al leer imagen");
	        }
 
        byte[] imagen = bos.toByteArray();
        
        

		
		System.out.println("Guardar empleado");
		empleado = new Empleado("2", empresa, "Empleado 2","direccion 2", "tipo Empleado2", "empleado col2",4000.55, 33.88, 40.55,imagen);
		
		empleadoService.save(empleado);
		
		empleado = empleadoService.findByDni("2");
		if (empleado == null)
		{
			fail("El empleado no se ha guardado");
		}
		
		//asertrue(empleado != null);

	}

}
