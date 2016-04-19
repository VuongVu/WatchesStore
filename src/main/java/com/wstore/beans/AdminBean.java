package com.wstore.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.Size;

import com.wstore.DAO.AdminDAO;
import com.wstore.entities.Admin;
import com.wstore.validator.UserNameExistsConstraint;


@ManagedBean(name = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@UserNameExistsConstraint
	@Size(min = 5, max = 30, message = "User name should be of length from 5 to 30 charst")
	private String userName = null;
	private Admin admin = new Admin();
	private List<Admin> admins = new ArrayList<Admin>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	/**
	 * get all admin account
	 * @return list
	 */
	public List<Admin> getAdmins() {
		AdminDAO dao = new AdminDAO();
		admins = dao.findAllAdmins();

		return admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	/**
	 * save new admin
	 */
	public String addAdmin(){
		AdminDAO dao = new AdminDAO();
		this.admin.setUserName(userName);
		dao.addAdmin(this.admin);
		//reset data
		admin = new Admin();
		userName = null;

		return "/pages/admin/admin.jsf?faces-redirect=true";

	}

	/**
	 * get edit action
	 */
	public String editAction(Admin admin){
		this.admin = admin;

		return "/pages/admin/edit.jsf?faces-redirect=true";
	}

	/**
	 * update categody
	 */
	public String updateAdmin() {
		AdminDAO dao = new AdminDAO();

		dao.updateAdmin(this.admin);
		//reset data
		this.admin = new Admin();

		return "/pages/admin/admin.jsf?faces-redirect=true";
	}

	/**
	 * delete category
	 */
	public void deleteCategory(Admin admin) {
		AdminDAO dao = new AdminDAO();
		dao.deleteAdmin(admin);
	}
}
