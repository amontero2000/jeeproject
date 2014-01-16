package es.microforum.repository;

import org.springframework.data.repository.CrudRepository;

import es.microforum.model.Empresa;


public interface EmpresaRepository extends CrudRepository<Empresa, String>{

}
