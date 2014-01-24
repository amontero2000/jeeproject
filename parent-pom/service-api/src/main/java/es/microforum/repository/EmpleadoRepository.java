package es.microforum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import es.microforum.model.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, String> {
	Page<Empleado> findByNombre(String name, Pageable pageable);

}
