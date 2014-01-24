package es.microforum.serviceapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.microforum.model.Empleado;
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
		
		//buscar Empresa por nombre devolver paginado
		public Page<Empresa> findByNombre(String name, Pageable pageable);

}
