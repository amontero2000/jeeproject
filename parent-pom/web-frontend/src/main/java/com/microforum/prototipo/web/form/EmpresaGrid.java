package com.microforum.prototipo.web.form;

import java.util.List;

import es.microforum.model.Empresa;


public class EmpresaGrid {

	private int totalPages;

	private int currentPage;

	private long totalRecords;

	private List<Empresa> empresaData;

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

	public List<Empresa> getEmpresaData() {
		return empresaData;
	}

	public void setEmpresaData(List<Empresa> empresaData) {
		this.empresaData = empresaData;
		for (Empresa empresa: empresaData)
		{
			empresa.setEmpleados(null);
		}
	}

}
