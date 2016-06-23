package com.wstore.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "comment")
@AssociationOverrides({ @AssociationOverride(name = "id.customer", joinColumns = @JoinColumn(name = "customerId")),
	@AssociationOverride(name = "id.product", joinColumns = @JoinColumn(name = "productId")) })

public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;
	private CommentPK id = new CommentPK();

	@EmbeddedId
	public CommentPK getId() {
		return id;
	}

	public void setId(CommentPK id) {
		this.id = id;
	}

	@SuppressWarnings("unused")
	private Customer customer;

	@Transient
	public Customer getCustomer() {
		return getId().getCustomer();
	}

	public void setCustomer(Customer customer) {
		getId().setCustomer(customer);
	}

	@SuppressWarnings("unused")
	private Product product;

	@Transient
	public Product getProduct() {
		return getId().getProduct();
	}

	public void setProduct(Product product) {
		getId().setProduct(product);
	}

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "rating")
	private Integer rating;

	@Column(name="isdel")
	private boolean isdel;
	
	@Column(name="date_create")
	private Date date_create;

	
	public Date getDate_create() {
		return date_create;
	}

	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}

	public boolean isIsdel() {
		return isdel;
	}

	public void setIsdel(boolean isdel) {
		this.isdel = isdel;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Comment that = (Comment) o;

		if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return (getId() != null ? getId().hashCode() : 0);
	}

}
