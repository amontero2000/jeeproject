package es.microforum.repository;

import org.springframework.data.repository.CrudRepository;

import es.microforum.model.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, String> {

}
