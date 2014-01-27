package es.microforum.serviceapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;


public interface EmpleadoService {
	
	// guardar empleado
		public Empleado guardar(Empleado empleado);
		
		// eliminar empleado
		public void eliminar(Empleado empleado);
		
		// buscar Empleado por dni
		public Empleado buscarPorDni(String id);
		
		// buscar todos los empleados
		public List<Empleado> buscarEmpleados();
		
		//buscar empleado por nombre devolver paginado
		public Page<Empleado> findByNombre(String name, Pageable pageable);
		
		Page<Empleado> findAll(Pageable pageable);
}
