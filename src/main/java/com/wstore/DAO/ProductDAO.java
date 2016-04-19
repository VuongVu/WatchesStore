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

import com.wstore.entities.Product;
import com.wstore.utils.HibernateUtil;

public class ProductDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDAO.class);

	/**
	 * add new product
	 */
	public void addProduct(Product product) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//save new product
			session.save(product);

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
	 * update product
	 */
	public void updateProduct(Product product) {
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

			//update product detail
			session.update(product);

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
	 * delete product
	 */
	public void deleteProduct(Product product) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//delete product object
			product.setDelete(true);
			session.update(product);

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
	 * get all product
	 */
	@SuppressWarnings("unchecked")
	public List<Product> findAllProducts() {
		List<Product> products = new ArrayList<Product>();
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
			Criteria criteria = session.createCriteria(Product.class);
			// To sort records in descening order
			criteria.add(Restrictions.eq("isDelete",false));
			criteria.addOrder(Order.asc("productId"));
			//get list from criteria
			products = criteria.list();

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
		return products;
	}

	public Product findOneProducts(int productId) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		Product product = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//call Criteria API
			Criteria criteria = session.createCriteria(Product.class);
			//get list from criteria
			criteria.add(Restrictions.eq("productId", productId)).add(Restrictions.eq("isDelete",false));

			product = (Product) criteria.uniqueResult();

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
		return product;
	}

	@SuppressWarnings("unchecked")
	public List<Product> findProductByCatId(int categoryId) {
		List<Product> products = new ArrayList<Product>();
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
			Criteria criteria = session.createCriteria(Product.class);
			// To sort records in descening order
			criteria.createAlias("category", "c");
			criteria.add(Restrictions.eq("c.categoryId", categoryId)).add(Restrictions.eq("isDelete",false));
			//get list from criteria
			products = criteria.list();

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
		return products;
	}

}
