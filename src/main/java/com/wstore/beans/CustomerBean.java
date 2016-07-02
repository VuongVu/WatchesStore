package com.wstore.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.wstore.DAO.CustomerDAO;
import com.wstore.entities.Customer;
import com.wstore.utils.SendMail;
import com.wstore.validator.EmailExistsConstraint;



@ManagedBean(name = "customerBean")
@SessionScoped
public class CustomerBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Size(min = 6, max = 30, message = "Email should be of length from 6 to 30 charst")
	@Email(message = "Email format is invalid")
	@EmailExistsConstraint
	private String email;
	private Customer customer = new Customer();
	private List<Customer> customers = new ArrayList<Customer>();
	private boolean editable=false;
	private boolean editpassword=false;
	
	@Email(message = "Email format is invalid")
	private String email_reset;

	
	public String getEmail_reset() {
		return email_reset;
	}
	public void setEmail_reset(String email_reset) {
		this.email_reset = email_reset;
	}
	public boolean isEditpassword() {
		return editpassword;
	}
	public void setEditpassword(boolean editpassword) {
		this.editpassword = editpassword;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Customer> getCustomers() {
		CustomerDAO dao = new CustomerDAO();
		customers = dao.findAllCustomers();

		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	/**
	 *
	 * register new user
	 * @throws InterruptedException 
	 */
	public String registerUser() throws InterruptedException {
		CustomerDAO dao = new CustomerDAO();
		LoginBean loginBean = (LoginBean)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "loginBean");
		
		customer.setEmail(this.email);
		dao.addCustomer(this.customer);
		
		loginBean.setEmail(email);
		loginBean.setPassword(customer.getPassword());
		
		customer = new Customer();
		email = null;

		return loginBean.userLogin();
	}
	
	public String registerCheckout() throws InterruptedException {
		CustomerDAO dao = new CustomerDAO();
		
		LoginBean loginBean = (LoginBean)FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "loginBean");		
		customer.setEmail(this.email);
		dao.addCustomer(this.customer);	
		
		loginBean.setEmail(email);
		loginBean.setPassword(customer.getPassword());
		
		this.customer = new Customer();
		email = null;
		return loginBean.userLoginCheckout();
	}

	/**
	 * add new customer
	 */
	public String addCustomer(){
		CustomerDAO dao = new CustomerDAO();
		customer.setEmail(this.email);
		dao.addCustomer(this.customer);
		customer = new Customer();
		email = null;

		return "/pages/customer/customer.jsf?faces-redirect=true";
	}

	/**
	 * get edit action
	 */
	public String editAction(Customer customer){
		this.customer = customer;

		return "/pages/customer/edit.jsf?faces-redirect=true";
	}

	/**
	 * update customer
	 */
	public String updateCustomer(){
		CustomerDAO dao = new CustomerDAO();
		dao.updateCustomer(this.customer);
		customer = new Customer();
		return "/pages/customer/customer.jsf?faces-redirect=true";
	}

	public String updateCustomerByUser(){
		CustomerDAO dao = new CustomerDAO();
		dao.updateCustomer(this.customer);
		customer = new Customer();
		this.editable=false;
		this.editpassword=false;
		return "/account.jsf?faces-redirect=true";
	}

	/**
	 * delete customer
	 */
	public void deleteCustomer(Customer customer) {
		CustomerDAO dao = new CustomerDAO();
		dao.deleteCustomer(customer);
	}

	public boolean takeEditable(){
		this.editable=true;
		this.editpassword=false;
		return this.editable;
	}

	public boolean takeEditPassword(){
		this.editpassword=true;
		this.editable=false;
		return this.editpassword;
	}

	public Customer findCustomerByEmail(String email){

		CustomerDAO dao=new CustomerDAO();
		this.customer=dao.findCustomerByEmail(email);
		return this.customer;
	}
	
	public String resetPassword(){
		String currentUrl = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		SendMail sendMail = new SendMail();
		CustomerDAO dao = new CustomerDAO();
		Customer customer = null;
		
		customer = dao.findCustomerByEmail(email_reset);
		if(customer != null){			
			sendMail.forgotPassword(email_reset, customer.getPassword());
			return currentUrl+"?faces-redirect=true";
			
		}else {
			FacesContext.getCurrentInstance().addMessage(
					"resetButton",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Email doesn't exists", "Please register nesw account"));
		}
		return null;
	}
	
	public String changePassword(String old_password, String new_password, String email){
		CustomerDAO dao = new CustomerDAO();
		Customer customer = null;
		String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		try {
			customer = dao.findCustomerByEmail(email);
			if(customer.getPassword().equals(old_password)){
				customer.setPassword(new_password);
				dao.updateCustomer(customer);
				return url+"?faces-redirect=true";
			}else {
				FacesContext.getCurrentInstance().addMessage(
						"resetPassword",
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Current password doesn't match", "Please fill another"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}
}
