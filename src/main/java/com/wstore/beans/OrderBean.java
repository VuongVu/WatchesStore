package com.wstore.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import com.wstore.DAO.CustomerDAO;
import com.wstore.DAO.OrderDAO;
import com.wstore.DAO.OrderDetailDAO;
import com.wstore.entities.Customer;
import com.wstore.entities.Order;
import com.wstore.entities.OrderDetail;


@ManagedBean(name = "orderBean", eager = true)
@SessionScoped
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Order order = new Order();
	private List<Order> orders = new ArrayList<Order>();
	private List<SelectItem> listCustomers = new ArrayList<SelectItem>();
	private Order order_detail = new Order();
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	

	public Order getOrder_detail() {
		return order_detail;
	}

	public void setOrder_detail(Order order_detail) {
		this.order_detail = order_detail;
	}

	/**
	 * get all order
	 *
	 * @return list
	 */
	public List<Order> getOrders() {
		OrderDAO dao = new OrderDAO();
		orders = dao.findAllOrders();

		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<SelectItem> getListCustomers() {
		CustomerDAO dao = new CustomerDAO();
		listCustomers.clear();
		for (Customer c : dao.findAllCustomers()) {
			listCustomers.add(new SelectItem(c, c.getEmail()));
		}

		return listCustomers;
	}

	public void setListCustomers(List<SelectItem> listCustomers) {
		this.listCustomers = listCustomers;
	}

	public String takeOrder(Order order){
		this.order_detail=order;
		return "order-detail.jsf?faces-redirect=true";
	}
	/**
	 * add new order
	 */
	public String addOrder() {
		OrderDAO dao = new OrderDAO();
		dao.addOrder(this.order);
		order = new Order();

		return "/pages/order/order.jsf?faces-redirect=true";
	}

	/**
	 * get edit action
	 */
	public String editAction(Order order) {
		this.order = order;

		return "/pages/order/edit.jsf?faces-redirect=true";
	}

	/**
	 * update order
	 */
	public String updateOrder() {
		OrderDAO dao = new OrderDAO();
		dao.updateOrder(this.order);
		order = new Order();

		return "/pages/order/order.jsf?faces-redirect=true";
	}

	/**
	 * delete order
	 */
	public void deleteOrder(Order order) {
		OrderDAO dao = new OrderDAO();
		dao.deleteOrder(order);
	}

	public List<Order> getOrderByCusEmail(String email) {
		List<Order> orders = new ArrayList<Order>();
		OrderDAO dao = new OrderDAO();
		orders = dao.findOrderByCustEmail(email);
		return orders;
	}

	public List<OrderDetail> getOderdetailByOrderId(int orderId) {
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		OrderDetailDAO dao = new OrderDetailDAO();
		orderDetails = dao.findOrderDetailByOrderId(orderId);
		return orderDetails;
	}

}
