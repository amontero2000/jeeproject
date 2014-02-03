package com.microforum.prototipo.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.microforum.prototipo.web.form.EmpleadoGrid;
import com.microforum.prototipo.web.form.Message;
import com.microforum.prototipo.web.util.UrlUtil;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceapi.EmpresaService;


@RequestMapping("/empleados")
@Controller
public class EmpleadoController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageSource messageSource;

	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private EmpresaService empresaService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("listar empleados");

		List<Empleado> empleados = empleadoService.buscarEmpleados();
		uiModel.addAttribute("empleados", empleados);

		logger.info("No. of empleados: " + empleados.size());

		return "empleados/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") String id, Model uiModel) {
		Empleado empleado = empleadoService.buscarPorDni(id);
		uiModel.addAttribute("empleado", empleado);
		return "empleados/show";
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Empleado empleado, BindingResult bindingResult,Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("modificando empleado");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message", 
					new Message("error", messageSource.getMessage("empleado_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("empleado", empleado);
			return "empleados/update";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute("message",
				new Message("success", messageSource.getMessage("empleado_save_success", new Object[] {}, locale)));

		empleadoService.guardar(empleado);
		return "redirect:/empleados/"
				+ UrlUtil.encodeUrlPathSegment(empleado.getDni().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model uiModel) {
		uiModel.addAttribute("empleado", empleadoService.buscarPorDni(id));
		return "empleados/update";
	}
	
	@RequestMapping(value = "/{id}", params = "formdelete", method = RequestMethod.GET)
	public String deleteForm(@PathVariable("id") String id, Model uiModel) {
		empleadoService.eliminar(empleadoService.buscarPorDni(id));
		return "empleados/list";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Empleado empleado, BindingResult bindingResult,	Model uiModel, HttpServletRequest httpServletRequest,RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Crear empleado");
		
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("message",	
					new Message("error", messageSource.getMessage("empleado_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("empleado", empleado);
			return "empleados/create";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute("message",	
				new Message("success", messageSource.getMessage("empleado_save_success", new Object[] {}, locale)));
		
		logger.info("Empleado id: " + empleado.getDni());

		empleado.setEmpresa(empresaService.buscarPorNif(empleado.getEmpresa().getNif()));
		empleadoService.guardar(empleado);
		return "redirect:/empleados/" + UrlUtil.encodeUrlPathSegment(empleado.getDni().toString(),httpServletRequest);
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		Empleado empleado = new Empleado();
		uiModel.addAttribute("empleado", empleado);
		uiModel.addAttribute("empresasList", empresaService.buscarEmpresas());
		
		return "empleados/create";
	}

	/**
	 * Support data for front-end grid
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public EmpleadoGrid listGrid(@RequestParam("name") String nombre,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		logger.info("Lista de empleados por pagina: {}, filas: {}", page,rows);
		logger.info("Lista de empleados por red con orden: {}, ordenado: {}",sortBy, order);

		PageRequest pageRequest = null;

		pageRequest = new PageRequest(page - 1, rows);

		EmpleadoGrid empleadoGrid = new EmpleadoGrid();
		Page<Empleado> empleados;
		
		if (nombre == null || nombre.equals("undefined") || nombre.equals("") ) {
			empleados = empleadoService.findAll(pageRequest);
			logger.info("Buscar por nombre: " + nombre);
			
		} else {
			empleados = empleadoService.findByNombre(nombre, pageRequest);
			logger.info("Buscar por todos: " + nombre);
		}
		empleadoGrid.setCurrentPage(empleados.getNumber() + 1);
		empleadoGrid.setTotalPages(empleados.getTotalPages());
		empleadoGrid.setTotalRecords(empleados.getTotalElements());
		empleadoGrid.setEmpleadoData(empleados.getContent());

		return empleadoGrid;
	}

	@RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] downloadPhoto(@PathVariable("id") String id) {

		Empleado empleado = empleadoService.buscarPorDni(id);

		if (empleado.getImagen() != null) {
			logger.info("Downloading photo for id: {} with size: {}",
					empleado.getDni(), empleado.getImagen().length);
		}

		return empleado.getImagen();
	}
	
}
