package com.wstore.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.validator.constraints.Email;

import com.wstore.DAO.AdminDAO;
import com.wstore.DAO.CustomerDAO;


@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable{

	private static final long serialVersionUID = 1L;
	//email is used to login to system
	@Email(message = "Email format is invalid.")
	private String email;
	private String password;
	private String adminUsername;
	private String adminPassword;
	private boolean loggedIn;
	private boolean adminloggedIn;

	public boolean isAdminloggedIn() {
		return adminloggedIn;
	}

	public void setAdminloggedIn(boolean adminloggedIn) {
		this.adminloggedIn = adminloggedIn;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	/**
	 *
	 * Login functional
	 * @throws InterruptedException
	 */
	public String userLogin() throws InterruptedException {
		String currentUrl = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		CustomerDAO dao = new CustomerDAO();
		//validate email and password to login
		String result = dao.authenticate(email, password);
		//check login result
		if(result == "exists"){ //check if email doesn't exists
			loggedIn = false;
			// Add View Faces Message
			FacesContext.getCurrentInstance().addMessage(
					"loginButton",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Email doesn't exists", "Please register new account"));
			return null;
		}else if(result == "fail") { //check email or password incorrect
			loggedIn = false;
			// Add View Faces Message
			FacesContext.getCurrentInstance().addMessage(
					"loginButton",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Incorrect Email or Password", "Please enter correct email and password"));
			return null;
		}else { // user login
			loggedIn = true;
			// Add View Faces Message
			FacesContext.getCurrentInstance().addMessage(
					"loginButton",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Welcome to WStore", email));
			return currentUrl + "?faces-redirect=true";
		}
	}
	
	public String userLoginCheckout() throws InterruptedException {
		CustomerDAO dao = new CustomerDAO();
		//validate email and password to login
		String result = dao.authenticate(email, password);
		System.out.println(result+" "+email+" "+password);
		//check login result
		
		if(result == "exists"){ //check if email doesn't exists
			loggedIn = false;
			System.out.println("if exist");
			// Add View Faces Message
			FacesContext.getCurrentInstance().addMessage(
					"loginButton",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Email doesn't exists", "Please register new account"));
			return null;
		}else if(result == "fail") { //check email or password incorrect
			loggedIn = false;
			System.out.println("if fail");
			// Add View Faces Message
			FacesContext.getCurrentInstance().addMessage(
					"loginButton",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Incorrect Email or Password", "Please enter correct email and password"));
			return null;
		}else { // user login
			loggedIn = true;
			System.out.println("if success "+ loggedIn);
			// Add View Faces Message
			FacesContext.getCurrentInstance().addMessage(
					"loginButton",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Welcome to WStore", email));
			return "checkout-address.jsf?faces-redirect=true";
		}
	}

	public String adminLogin(){
		AdminDAO dao = new AdminDAO();
		String result = dao.authenticate(this.adminUsername, this.adminPassword);
		if(result == "exists"){
			adminloggedIn=false;
			FacesContext.getCurrentInstance().addMessage(
					"loginButton",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"User name doesn't exists, Please register new account", null));
			return null;
		}else if(result == "fail") { //check email or password incorrect
			adminloggedIn=false;
			// Add View Faces Message
			FacesContext.getCurrentInstance().addMessage(
					"loginButton",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect User name or Password, Please enter correct email and password", null));
			return null;
		}else { // user login
			adminloggedIn=true;
			return "/admin.jsf?faces-redirect=true";
		}
	}

	/**
	 * logout user funtional
	 */
	public String userLogout() {
		String currentUrl = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		loggedIn = false;
		email = null;
		password = null;
		return currentUrl + "faces-redirect=true";
	}
	/**
	 * logout admin
	 *
	 */
	public String adminLogout() {
		adminloggedIn = false;
		return null;
	}
}
