package com.wstore.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "category")
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryId")
	private int categoryId;

	@Size(min = 3, max = 30, message = "Category Name should be of length from 3 to 30 charst")
	@Column(name = "categoryName")
	private String categoryName;

	@Size(max = 50, message = "Category Description should be max of length 50 charst")
	@Column(name = "categoryDescription")
	private String categoryDescription;

	@OneToMany(mappedBy = "category")
	private Set<Product> products;

	@Column(name="isDelete")
	private boolean isDelete;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Category() {

	}

	public Category(String categoryName, String categoryDescription) {
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public boolean equals(Object object) {
		if(this.categoryId == ((Category) object).categoryId) {
			return true;
		}else {
			return false;
		}
	}

}
