package com.microforum.prototipo.web.form;

import java.util.List;

import es.microforum.model.Empleado;


public class EmpleadoGrid {

	private int totalPages;

	private int currentPage;

	private long totalRecords;

	private List<Empleado> empleadoData;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<Empleado> getEmpleadoData() {
		return empleadoData;
	}

	public void setEmpleadoData(List<Empleado> empleadoData) {
		this.empleadoData = empleadoData;
		
		for(Empleado empleado: empleadoData)
		{
			empleado.setEmpresa(null);
		}
	}

}
