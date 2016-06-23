package com.wstore.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "customer")
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerId")
	private int customerId;

	@Size(min = 6, max = 30, message = "Email should be of length from 6 to 30 charst")
	@Email(message = "Email format is invalid")
	@Column(name = "email")
	private String email;

	@Size(min = 6, max = 30, message = "Password should be of length from 6 to 30 charst")
	@Column(name = "password")
	private String password;

	@Size(min = 6, max = 30, message = "Full Name should be of length from 6 to 30 charst")
	@Column(name = "fullName")
	private String fullName;

	@Size(min = 10, max = 12, message = "Phone should be of length from 10 to 12 charst")
	@Column(name = "phone")
	private String phone;

	@Column(name="isDelete")
	private boolean isDelete;


	@OneToMany(mappedBy = "customer")
	private Set<Order> orders;

	@OneToMany(fetch=FetchType.LAZY,mappedBy = "id.customer",cascade=javax.persistence.CascadeType.ALL)
	private Set<Comment> comments;

	@OneToMany(mappedBy="customer")
	private Set<Delivery> deliveries;
	public Customer() {

	}

	public Customer(int customerId, String email, String password, String fullName, String phone) {
		super();
		this.customerId = customerId;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.phone = phone;
	}

	
	public Set<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(Set<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}


	@Override
	public boolean equals(Object object) {
		if(this.customerId == ((Customer) object).customerId) {
			return true;
		}else {
			return false;
		}
	}
}
