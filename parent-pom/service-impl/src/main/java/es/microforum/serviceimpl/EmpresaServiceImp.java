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
	
	public Empresa guardar(Empresa empresa) {
		// TODO Auto-generated method stub
		return empresaRepository.save(empresa);
	}

	public void eliminar(Empresa empresa) {
		// TODO Auto-generated method stub
		empresaRepository.delete(empresa);	
	}
	
	public Empresa buscarPorNif(String id) {
		return empresaRepository.findOne(id);
	}
	
	public List<Empresa> buscarEmpresas() {
		return Lists.newArrayList(empresaRepository.findAll());
	}
	
	public void commit() {
		 empresaRepository.notifyAll();
	}
	
}
