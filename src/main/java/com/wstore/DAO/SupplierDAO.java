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

import com.wstore.entities.Supplier;
import com.wstore.utils.HibernateUtil;

public class SupplierDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierDAO.class);

	/**
	 * add new Supplier
	 */
	public void addSupplier(Supplier supplier) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//save supplier object
			session.save(supplier);

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
	 * update Supplier
	 */
	public void updateSupplier(Supplier supplier) {
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

			//update supplier detail
			session.update(supplier);

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
	 * delete Supplier
	 */
	public void deleteSupplier(Supplier supplier) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			supplier.setDelete(true);
			//delete supplier object
			session.update(supplier);

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
	 * get all Supplier
	 */
	@SuppressWarnings("unchecked")
	public List<Supplier> findAllSuppliers() {
		List<Supplier> suppliers = new ArrayList<Supplier>();
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
			Criteria criteria = session.createCriteria(Supplier.class);
			// To sort records in descening order
			criteria.add(Restrictions.eq("isDelete",false));
			criteria.addOrder(Order.asc("supplierId"));
			//get list from criteria
			suppliers = criteria.list();

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
		return suppliers;
	}

	/**
	 * get supplier by Id
	 */
	public Supplier findSupplierById(int supplierId){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		Supplier supplier = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Supplier.class);
			criteria.add(Restrictions.eq("supplierId", supplierId)).add(Restrictions.eq("isDelete",false));
			supplier = (Supplier)criteria.uniqueResult();

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
		return supplier;
	}
}
