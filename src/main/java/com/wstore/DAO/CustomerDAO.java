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

import com.wstore.entities.Customer;
import com.wstore.utils.HibernateUtil;


public class CustomerDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDAO.class);

	/**
	 * login authentication
	 * @param email
	 * @param password
	 *
	 */
	public String authenticate(String email, String password) {
		Customer customer = findCustomerByEmail(email);
		String result = null;
		if(customer == null){
			result = "exists";
		}else if(customer != null && customer.getEmail().equals(email) && customer.getPassword().equals(password)){
			result = "success";
		}else {
			result = "fail";
		}
		return result;
	}

	/**
	 *
	 * add new customer
	 */
	public void addCustomer(Customer customer){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//save customer object
			session.save(customer);

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
	 * Edit customer
	 */
	public void updateCustomer(Customer customer) {
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

			//update customer detail
			session.update(customer);

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
	 * delete customer
	 */
	public void deleteCustomer(Customer customer){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			customer.setDelete(true);
			session.update(customer);

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
	 * get all Customer
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> findAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
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
			Criteria criteria = session.createCriteria(Customer.class);
			// To sort records in descening order
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.addOrder(Order.asc("customerId"));
			//get list from criteria
			customers = criteria.list();

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
		return customers;
	}

	/**
	 * find customer by id
	 */
	public Customer findCustomerById(int customerId){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		Customer customer = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("customerId", customerId)).add(Restrictions.eq("isDelete",false));
			customer = (Customer)criteria.uniqueResult();

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
		return customer;
	}

	/**
	 * get Customer by email
	 * @param email
	 * @return
	 */
	public Customer findCustomerByEmail(String email){
		Customer customer = null;
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		try
		{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//call Criteria API
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("email", email)).add(Restrictions.eq("isDelete",false));
			customer = (Customer)criteria.uniqueResult();

			//commit transaction
			tx.commit();
		}catch(HibernateException e){
			if (tx != null) {
				tx.rollback();
				e.printStackTrace();
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
		return customer;
	}

	/**
	 * check email has been exists
	 * @param email
	 * @return boolean
	 */
	public boolean isEmailExists(String email) {
		boolean result = false;
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		try
		{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//call Criteria API
			Criteria criteria = session.createCriteria(Customer.class);
			criteria.add(Restrictions.eq("email", email)).add(Restrictions.eq("isDelete",false));
			Customer customer = (Customer)criteria.uniqueResult();

			//commit transaction
			tx.commit();
			//check email has exists
			if(customer != null){
				result = true;
			}
		}catch(HibernateException e){
			if (tx != null) {
				tx.rollback();
				result = false;
				e.printStackTrace();
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
		return result;
	}
}
