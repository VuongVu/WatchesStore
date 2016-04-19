package com.wstore.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orderdetail")
public class OrderDetail implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderDetailId")
	private int orderDetailId;

	@Column(name = "price")
	private double price;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "discount")
	private double discount;

	//@Temporal(TemporalType.DATE)
	@Column(name = "billDate")
	private String billDate;

	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "orderId")
	private Order order;

	@Column(name="isDelete")
	private boolean isDelete;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public OrderDetail() {

	}

	public OrderDetail(double price, int quantity, double discount, String billDate,
			Product product, Order order) {
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
		this.billDate = billDate;
		this.product = product;
		this.order = order;
	}

	public int getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(int orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}


}
