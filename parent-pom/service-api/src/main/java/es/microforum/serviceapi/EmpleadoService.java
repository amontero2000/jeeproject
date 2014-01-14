package es.microforum.serviceapi;

import es.microforum.model.Empleado;


public interface EmpleadoService {
	
	// guardar empleado
		public Empleado guardar(Empleado empleado);
		
		// eliminar empleado
		public void eliminar(Empleado empleado);

}
