package com.wstore.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.wstore.DAO.OrderDetailDAO;
import com.wstore.entities.OrderDetail;

@ManagedBean(name = "orderDetailBean")
@SessionScoped
public class OrderDetailBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private OrderDetail orderDetail;
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}
	public List<OrderDetail> getOrderDetails() {
		OrderDetailDAO dao = new OrderDetailDAO();
		orderDetails = dao.findAllOrderDetail();

		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	/**
	 * get edit action
	 */
	public String editAction(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;

		return "/pages/orderdetail/edit.jsf?faces-redirect=true";
	}

	/**
	 * update order
	 */
	public String updateOrderDetail() {
		OrderDetailDAO dao = new OrderDetailDAO();
		dao.updateOrderDetail(orderDetail);
		orderDetail = new OrderDetail();

		return "/pages/orderdetail/order-detail.jsf?faces-redirect=true";
	}

	public String deleteOrderDetail(OrderDetail orderDetail) {
		OrderDetailDAO dao = new OrderDetailDAO();
		dao.deleteOrderDetail(orderDetail);
		return "/pages/orderdetail/order-detail.jsf?faces-redirect=true";
	}
}
