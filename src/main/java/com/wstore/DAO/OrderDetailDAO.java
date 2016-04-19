package com.wstore.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wstore.entities.OrderDetail;
import com.wstore.utils.HibernateUtil;

public class OrderDetailDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailDAO.class);

	@SuppressWarnings("unchecked")
	public List<OrderDetail> findAllOrderDetail() {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		try {
			// get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(OrderDetail.class);
			criteria.createAlias("order", "o");
			criteria.addOrder(Order.asc("orderDetailId"));
			criteria.add(Restrictions.eq("isDelete", false));
			orderDetails = criteria.list();

			// commit transaction
			tx.commit();

			return orderDetails;
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
				// result = "fail";
			}
		} finally {
			if (!sessionFactory.isClosed()) {
				session.close();
			}
		}
		return orderDetails;
	}

	public void updateOrderDetail(OrderDetail orderDetail) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		// String result = null;

		try {
			// get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			// update order detail
			session.save(orderDetail);

			// commit transaction
			tx.commit();
			// result = "success";
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
				// result = "fail";
			}
		} finally {
			if (!sessionFactory.isClosed()) {
				session.close();
			}
		}
	}

	public void deleteOrderDetail(OrderDetail orderDetail) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		// String result = null;

		try {
			// get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			orderDetail.setDelete(true);
			// delete order detail
			session.delete(orderDetail);

			// commit transaction
			tx.commit();
			// result = "success";
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
				// result = "fail";
			}
		} finally {
			if (!sessionFactory.isClosed()) {
				session.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<OrderDetail> findOrderDetailByOrderId(int orderId) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		try {
			// get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(OrderDetail.class);
			criteria.createAlias("order", "o");
			criteria.add(Restrictions.eq("o.orderId", orderId));
			orderDetails = criteria.list();

			// commit transaction
			tx.commit();
			// result = "success";
			return orderDetails;
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
				// result = "fail";
			}
		} finally {
			if (!sessionFactory.isClosed()) {
				session.close();
			}
		}
		return null;
	}
}
