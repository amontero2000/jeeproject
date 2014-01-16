package es.microforum.serviceimpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empresa;
import es.microforum.repository.EmpresaRepository;
import es.microforum.serviceapi.EmpresaService;

/**
 * @author Clarence
 *
 */
@Service("springJpaEmpresaService")
@Repository
@Transactional


public class EmpresaServiceImp implements EmpresaService{
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public Empresa save(Empresa empresa) {
		// TODO Auto-generated method stub
		return empresaRepository.save(empresa);
	}

	public void delete(Empresa empresa) {
		// TODO Auto-generated method stub
		empresaRepository.delete(empresa);	
	}
	
	//llamar al repositorio y devolver lo que te devuelve el repositorio
	public Empresa findByNif(String id) {
		return empresaRepository.findOne(id);
	}
}
