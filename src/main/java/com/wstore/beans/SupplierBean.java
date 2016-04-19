package com.wstore.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.wstore.DAO.SupplierDAO;
import com.wstore.entities.Supplier;



@ManagedBean(name = "supplierBean")
@SessionScoped
public class SupplierBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private Supplier supplier = new Supplier();
	private List<Supplier> suppliers = new ArrayList<Supplier>();

	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public List<Supplier> getSuppliers() {
		SupplierDAO dao = new SupplierDAO();
		suppliers = dao.findAllSuppliers();

		return suppliers;
	}
	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	/**
	 * save new supplier
	 */
	public String addSupplier(){
		SupplierDAO dao = new SupplierDAO();
		dao.addSupplier(this.supplier);
		//reset data
		this.supplier = new Supplier();
		return "/pages/supplier/supplier.jsf?faces-redirect=true";
	}

	/**
	 * get edit action
	 */
	public String editAction(Supplier supplier){
		this.supplier = supplier;
		return "/pages/supplier/edit.jsf?faces-redirect=true";
	}

	/**
	 * update supplier
	 */
	public String updateSupplier() {
		SupplierDAO dao = new SupplierDAO();
		dao.updateSupplier(this.supplier);
		//reset data
		this.supplier = new Supplier();
		return "/pages/supplier/supplier.jsf?faces-redirect=true";
	}

	/**
	 * delete supplier
	 */
	public void deleteSupplier(Supplier supplier) {
		SupplierDAO dao = new SupplierDAO();
		dao.deleteSupplier(supplier);
	}
}
