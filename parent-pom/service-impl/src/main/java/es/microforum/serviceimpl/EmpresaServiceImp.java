package es.microforum.serviceimpl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empleado;
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
	
	@Override
	public Empresa guardar(Empresa empresa) {
		// TODO Auto-generated method stub
		return empresaRepository.save(empresa);
	}

	@Override
	public void eliminar(Empresa empresa) {
		// TODO Auto-generated method stub
		empresaRepository.delete(empresa);	
	}
	
	@Override
	public Empresa buscarPorNif(String id) {
		return empresaRepository.findOne(id);
	}
	
	@Override
	public List<Empresa> buscarEmpresas() {
		return Lists.newArrayList(empresaRepository.findAll());
	}
	
	@Override
	public Page<Empresa> findByNombre(String name, Pageable pageable)
	{
		//return empresaRepository.findByNombre(name, pageable);
		return empresaRepository.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Page<Empresa> findAll(Pageable pageable) {
		return empresaRepository.findAll(pageable);
	}
		
}
