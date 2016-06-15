package com.wstore.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "`order`")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private int orderId;

	//@Temporal(TemporalType.DATE)
	@Column(name = "orderDate")
	private String orderDate;

	@Column(name = "total_amount")
	private Double total_amount;

	@Column(name = "paid")
	private boolean paid;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToMany(mappedBy = "order")
	private Set<OrderDetail> orderDetails;

	@Column(name="isDelete")
	private boolean isDelete;

	@Column(name="delivery_address")
	private String delivery_address;
	
	@Column(name="receiver")
	private String receiver;
	
	@Column(name="receiver_phone")
	private String receiver_phone;
	
	@Column(name="fastDelivery")
	private boolean fastDelivery;
	
	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Order() {

	}

	public Order(String orderDate, Double total_amount, boolean paid,
			Customer customer) {
		this.orderDate = orderDate;
		this.total_amount = total_amount;
		this.paid = paid;
		this.customer = customer;
	}

	
	public String getDelivery_address() {
		return delivery_address;
	}

	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiver_phone() {
		return receiver_phone;
	}

	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}

	public boolean isFastDelivery() {
		return fastDelivery;
	}

	public void setFastDelivery(boolean fastDelivery) {
		this.fastDelivery = fastDelivery;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
}
