package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apress.prospring3.ch10.domain.Persona;
import com.apress.prospring3.ch10.repository.ContactRepository;
import com.apress.prospring3.ch10.repository.PersonaRepository;
import com.apress.prospring3.ch10.service.ContactService;
import com.apress.prospring3.ch10.service.PersonaService;
import com.google.common.collect.Lists;

import es.microforum.model.Empleado;
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
