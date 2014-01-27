package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.repository.EmpleadoRepository;
import es.microforum.serviceapi.EmpleadoService;

/**
 * @author Clarence
 *
 */
@Service("springJpaEmpleadoService")
@Repository
@Transactional

public class EmpleadoServiceImp implements EmpleadoService {
	
	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Override
	public Empleado guardar(Empleado empleado) {
		// TODO Auto-generated method stub
		return empleadoRepository.save(empleado);
	}
	
	@Override
	public void eliminar(Empleado empleado) {
		empleadoRepository.delete(empleado);	
	}
	
	//llamar al repositorio y devolver lo que te devuelve el repositorio
	@Override
	public Empleado buscarPorDni(String id) {
		return empleadoRepository.findOne(id);
	}
	
	@Override
	public List<Empleado> buscarEmpleados() {
		return Lists.newArrayList(empleadoRepository.findAll());
	}
	
	@Transactional(readOnly = true)
	public Page<Empleado> findByNombre(String name, Pageable pageable)
	{
		//return empleadoRepository.findByNombre(name, pageable);
		return empleadoRepository.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Page<Empleado> findAll(Pageable pageable) {
		return empleadoRepository.findAll(pageable);
	}
	
}
