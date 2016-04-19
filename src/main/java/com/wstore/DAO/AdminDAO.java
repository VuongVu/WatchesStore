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

import com.wstore.entities.Admin;
import com.wstore.utils.HibernateUtil;

public class AdminDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDAO.class);

	/**
	 * ad new admin account
	 */
	public void addAdmin(Admin admin) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//save admin object
			session.save(admin);

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
	 * Edit admin account
	 */
	public void updateAdmin(Admin admin) {
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

			//update admin detail
			session.update(admin);

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
	 * delete admin
	 */
	public void deleteAdmin(Admin admin){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			session.delete(admin);

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
	 * get all admin
	 */
	@SuppressWarnings("unchecked")
	public List<Admin> findAllAdmins() {
		List<Admin> admins = new ArrayList<Admin>();
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
			Criteria criteria = session.createCriteria(Admin.class);
			// To sort records in descening order
			criteria.addOrder(Order.asc("id"));
			//get list from criteria
			admins = criteria.list();

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
		return admins;
	}

	public Admin findAdminByUserName(String userName) {
		Admin admin =new Admin();
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
			Criteria criteria = session.createCriteria(Admin.class);
			criteria.add(Restrictions.eq("userName", userName));
			admin = (Admin)criteria.uniqueResult();

			//commit transaction
			tx.commit();
			//check email has exists
			if(admin != null){
				return admin;
			}
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
		return null;
	}

	public String authenticate(String username,String password){
		Admin admin=findAdminByUserName(username);

		String result = null;
		if(admin == null){
			result = "exists";
		}else if(admin != null && admin.getUserName().equals(username) && admin.getPassword().equals(password)){
			result = "success";
		}else {
			result = "fail";
		}
		return result;
	}
}
