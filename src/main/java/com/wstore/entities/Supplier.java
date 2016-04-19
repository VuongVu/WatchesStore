package com.wstore.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name = "supplier")
public class Supplier implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplierId")
	private int supplierId;

	@Size(min = 6, max = 50, message = "Company Name should be of length from 6 to 50 charst")
	@Column(name = "companyName")
	private String companyName;

	@Size(max = 100, message = "Address should be max of length 100 charst")
	@Column(name = "address")
	private String address;

	@Size(max = 30, message = "Country should be max of length 30 charst")
	@Column(name = "country")
	private String country;

	@Size(max = 30, message = "Phone should be max of length 30 charst")
	@Column(name = "phone")
	private String phone;

	@Email(message = "Email format is invalid!")
	@Size(min = 6, max = 30, message = "Email should be of length from 6 to 30 charst")
	@Column(name = "email")
	private String email;

	@Size(max = 30, message = "Fax should be max of length 30 charst")
	@Column(name = "fax")
	private String fax;

	@Size(max = 30, message = "Url should be max of length 30 charst")
	@Column(name = "url")
	private String url;

	@OneToMany(mappedBy = "supplier")
	private Set<Product> products = new HashSet<Product>();

	@Column(name="isDelete")
	private boolean isDelete;


	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Supplier() {

	}

	public Supplier(String companyName, String address, String country,
			String phone, String email, String fax, String url) {
		this.companyName = companyName;
		this.address = address;
		this.country = country;
		this.phone = phone;
		this.email = email;
		this.fax = fax;
		this.url = url;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public boolean equals(Object object) {
		if(this.supplierId == ((Supplier) object).supplierId) {
			return true;
		}else {
			return false;
		}
	}

}
