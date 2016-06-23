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

import com.wstore.entities.Delivery;
import com.wstore.utils.HibernateUtil;

public class DeliveryDAO {

	public void addDelivery(Delivery delivery){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//save Delivery object
			session.save(delivery);

			//commit transaction
			tx.commit();
		}catch (HibernateException e){
			if (tx != null) {
				tx.rollback();
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Delivery> findAllDeliveries(int id) {
		List<Delivery> deliveries = new ArrayList<Delivery>();
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
			Criteria criteria = session.createCriteria(Delivery.class);
			// To sort records in descening order
			criteria.createAlias("customer", "c");
			criteria.add(Restrictions.eq("c.customerId", id));
			criteria.addOrder(Order.desc("deliveryId"));
			//get list from criteria
			deliveries = criteria.list();

			//commit transaction
			tx.commit();

		}catch(HibernateException e){
			if (tx != null) {
				tx.rollback();
				
			}
		}finally {
			if(!sessionFactory.isClosed()){
				session.close();
			}
		}
		return deliveries;
	}
	
}
