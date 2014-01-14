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

import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;

/**
 * @author Clarence
 *
 */
@Service("springJpaPersonaService")
@Repository
@Transactional


public class EmpresaServiceImp implements EmpresaService{
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public Empresa guardar(Empresa empresa) {
		// TODO Auto-generated method stub
		return empresaRepository.save(empresa);
	}

	public void delete(Empresa empresa) {
		// TODO Auto-generated method stub
		empresaRepository.delete(empresa);	
	}


}
