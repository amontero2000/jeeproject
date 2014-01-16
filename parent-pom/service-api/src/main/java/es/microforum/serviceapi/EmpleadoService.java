package es.microforum.serviceapi;

import es.microforum.model.Empleado;


public interface EmpleadoService {
	
	   // guardar empleado
		public Empleado save(Empleado empleado);
		
		// eliminar empleado
		public void delete(Empleado empleado);
		
		// buscar Empleado por dni
		public Empleado findByDni(String id);

}
