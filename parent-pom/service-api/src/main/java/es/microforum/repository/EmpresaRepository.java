package es.microforum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.microforum.model.Empresa;

public interface EmpresaRepository extends PagingAndSortingRepository<Empresa, String>{
	public Page<Empresa> findByNombre(String nombre, Pageable pageable);

}
