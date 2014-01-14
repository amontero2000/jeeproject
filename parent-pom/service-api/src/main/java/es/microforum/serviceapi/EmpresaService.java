package es.microforum.serviceapi;

import es.microforum.model.Empresa;



public interface EmpresaService {
	
	
	// guardar empresa
		public Empresa guardar(Empresa empresa);
		
		// eliminar empresa
		public void eliminar(Empresa empresa);

}
