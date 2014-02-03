package es.microforum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.microforum.model.Empleado;

public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, String> {
	Page<Empleado> findByNombre(String nombre, Pageable pageable);
}
