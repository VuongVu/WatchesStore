package com.wstore.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

import javax.persistence.ManyToOne;

@Embeddable
public class CommentPK implements Serializable {
	private static final long serialVersionUID = 1L;
	private Customer customer;
	private Product product;

	@ManyToOne
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@ManyToOne
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CommentPK that = (CommentPK) o;

		if (customer != null ? !customer.equals(that.customer) : that.customer != null)
			return false;
		if (product != null ? !product.equals(that.product) : that.product != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (customer != null ? customer.hashCode() : 0);
		result = 31 * result + (product != null ? product.hashCode() : 0);
		return result;
	}
}
