package com.wstore.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="product_state")
public class ProductState implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="productId")
	private Product product;
	
	@Column(name="isNew")
	private boolean isNewProduct;
	
	@Column(name="isSale")
	private boolean isSale;
	
	@Column(name="isBest")
	private boolean isBest;
	
	@Column(name="isAll")
	private boolean isAll;
	
	@Column(name="isComing")
	private boolean isComing;
	
	
	
	public boolean isComing() {
		return isComing;
	}
	public void setComing(boolean isComing) {
		this.isComing = isComing;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public boolean isNewProduct() {
		return isNewProduct;
	}
	public void setNewProduct(boolean isNewProduct) {
		this.isNewProduct = isNewProduct;
	}
	public boolean isSale() {
		return isSale;
	}
	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}
	public boolean isBest() {
		return isBest;
	}
	public void setBest(boolean isBest) {
		this.isBest = isBest;
	}
	public boolean isAll() {
		return isAll;
	}
	public void setAll(boolean isAll) {
		this.isAll = isAll;
	}
	
}
