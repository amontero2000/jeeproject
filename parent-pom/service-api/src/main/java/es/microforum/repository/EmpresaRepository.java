package es.microforum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;


public interface EmpresaRepository extends CrudRepository<Empresa, String>{
	Page<Empresa> findByNombre(String name, Pageable pageable);

}
