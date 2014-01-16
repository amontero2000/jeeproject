package es.microforum.serviceapi;

import es.microforum.model.Empresa;



public interface EmpresaService {
	
	
	// guardar empresa
		public Empresa save(Empresa empresa);
		
		// eliminar empresa
		public void delete(Empresa empresa);
		
		// buscar Empresa por -nif
		public Empresa findByNif(String id);

}