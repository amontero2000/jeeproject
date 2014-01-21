package es.microforum.serviceapi;

import java.util.List;

import es.microforum.model.Empresa;



public interface EmpresaService {
	
	
	// guardar empresa
		public Empresa guardar(Empresa empresa);
		
		// eliminar empresa
		public void eliminar(Empresa empresa);
		
		// buscar Empresa por -nif
		public Empresa buscarPorNif(String id);
		
		// buscar Empresa por -nif
		public List<Empresa> buscarEmpresas();
		
		public void commit();

}
