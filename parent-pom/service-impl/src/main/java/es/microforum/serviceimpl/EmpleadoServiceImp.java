package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empleado;
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
	
	
	public EmpleadoServiceImp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Empleado save(Empleado empleado) {
		// TODO Auto-generated method stub
		return empleadoRepository.save(empleado);
	}

	public void delete(Empleado empleado) {
		// TODO Auto-generated method stub
		empleadoRepository.delete(empleado);	
	}
	
	//llamar al repositorio y devolver lo que te devuelve el repositorio
		public Empleado findByDni(String id) {
			return empleadoRepository.findOne(id);
		}
}
