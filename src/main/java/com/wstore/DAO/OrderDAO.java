package com.wstore.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wstore.entities.Order;
import com.wstore.utils.HibernateUtil;

public class OrderDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDAO.class);

	/**
	 *
	 * add new order
	 */
	public void addOrder(Order order){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//save order object
			session.save(order);

			//commit transaction
			tx.commit();
		}catch (HibernateException e){
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
	}

	/**
	 * Edit order
	 */
	public void updateOrder(Order order) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		//String result = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//update order detail
			session.save(order);

			//commit transaction
			tx.commit();
			//result = "success";
		}catch (HibernateException e){
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
				//result = "fail";
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}

		//return result;
	}

	/**
	 * delete Order
	 */
	public void deleteOrder(Order order){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			order.setDelete(true);
			session.update(order);

			//commit transaction
			tx.commit();

		}catch (HibernateException e){
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
	}

	/**
	 * get all Order
	 */
	@SuppressWarnings("unchecked")
	public List<Order> findAllOrders() {
		List<Order> orders = new ArrayList<Order>();
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//call Criteria API
			Criteria criteria = session.createCriteria(Order.class);
			// To sort records in descening order
			//criteria.addOrder(Order.asc("categoryId"));
			//get list from criteria
			criteria.add(Restrictions.eq("isDelete", false));
			orders = criteria.list();

			//commit transaction
			tx.commit();

		}catch(HibernateException e){
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
		return orders;
	}

	/**
	 * get Order by Id
	 */
	public Order findOrderById(int orderId){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		Order order = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Order.class);
			criteria.add(Restrictions.eq("orderId", orderId)).add(Restrictions.eq("isDelete",false));
			order = (Order)criteria.uniqueResult();

			//commit transaction
			tx.commit();

		}catch(HibernateException e){
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
		return order;
	}

	@SuppressWarnings("unchecked")
	public static List<Order> findOrderByCustEmail(String email){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		List<Order> orders = new ArrayList<Order>();

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("customer", "c");
			criteria.add(Restrictions.eq("c.email", email)).add(Restrictions.eq("isDelete", false));
			criteria.addOrder(org.hibernate.criterion.Order.desc("orderId"));
			orders = criteria.list();

			//commit transaction
			tx.commit();

		}catch(HibernateException e){
			if (tx != null) {
				tx.rollback();
				LOGGER.info(e.getMessage());
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
		return orders;

	}
}
