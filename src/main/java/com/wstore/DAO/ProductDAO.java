package com.wstore.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
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
			criteria.addOrder(Order.asc("productId")).add(Restrictions.eq("isDelete", false));
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
	
	@SuppressWarnings("unchecked")
	public List<Product> findNewProducts() {
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
			//criteria.add(Restrictions.eq("isDelete",false));
			criteria.createAlias("productStates", "ps");
			criteria.add(Restrictions.eq("ps.isNewProduct", true)).add(Restrictions.eq("isDelete", false));
			//criteria.addOrder(Order.asc("productId"));
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
	
	@SuppressWarnings("unchecked")
	public List<Product> findComingProducts() {
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
			//criteria.add(Restrictions.eq("isDelete",false));
			criteria.createAlias("productStates", "ps");
			criteria.add(Restrictions.eq("ps.isComing", true)).add(Restrictions.eq("isDelete", false));
			//criteria.addOrder(Order.asc("productId"));
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
	
	@SuppressWarnings("unchecked")
	public List<Product> findSaleProducts() {
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
			//criteria.add(Restrictions.eq("isDelete",false));
			criteria.createAlias("productStates", "ps");
			criteria.add(Restrictions.eq("ps.isSale", true)).add(Restrictions.eq("isDelete", false));
			//criteria.addOrder(Order.asc("productId"));
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
	
	@SuppressWarnings("unchecked")
	public List<Product> findBestProducts() {
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
			//criteria.add(Restrictions.eq("isDelete",false));
			criteria.createAlias("productStates", "ps");
			criteria.add(Restrictions.eq("ps.isBest", true)).add(Restrictions.eq("isDelete", false));
			//criteria.addOrder(Order.asc("productId"));
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
	
	@SuppressWarnings("unchecked")
	public List<Product> filter(int categoryId, String productBrand, double productPrice, String property,int sort){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		List<Product> list=new ArrayList<>();
		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			
			criteria.createAlias("category", "c");
			criteria.add(Restrictions.eq("c.categoryId", categoryId));
			criteria.add(Restrictions.like("productBrand",productBrand ));
			criteria.add(Restrictions.le("productPrice",productPrice));
			criteria.createAlias("productStates", "ps");
			criteria.add(Restrictions.eq("ps."+property+"", true));
			criteria.add(Restrictions.eq("isDelete", false));
			if(sort==0){
				criteria.addOrder(Order.asc("productId"));
			}else if (sort==1) {
				criteria.addOrder(Order.asc("productName"));
			}else if (sort==2) {
				criteria.addOrder(Order.desc("productName"));
			}else if (sort==3) {
				criteria.addOrder(Order.asc("productPrice"));
			}else if (sort==4) {
				criteria.addOrder(Order.desc("productPrice"));
			}
			list=criteria.list();
			

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
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> search(String search){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		boolean flag=false;
		double a = 0;
		List<Product> list=new ArrayList<>();
		try {
			a = Double.parseDouble(search);
			flag=true;
		} catch (Exception e) {
			
		}
		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("category", "ca");
			Criterion cri1 = Restrictions.like("productBrand", "%"+search+"%");
			Criterion cri2 = Restrictions.like("productName", "%"+search+"%");			
			Criterion cri4 = Restrictions.like("productDescription", "%"+search+"%");
			Criterion cri5 = Restrictions.like("ca.categoryName", "%"+search+"%");
			if (flag) {
				Criterion cri3 = Restrictions.eq("productPrice", a);
				criteria.add(Restrictions.or(cri1,cri2,cri3,cri4,cri5)).add(Restrictions.eq("isDelete", false));
			} else {
				criteria.add(Restrictions.or(cri1,cri2,cri4,cri5)).add(Restrictions.eq("isDelete", false));
			}
			
			list=criteria.list();
			

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
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findProductByBrand(String brand) {
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
			
			criteria.add(Restrictions.eq("productBrand", brand)).add(Restrictions.eq("isDelete",false));
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
