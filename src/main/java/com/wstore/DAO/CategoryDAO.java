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

import com.wstore.entities.Category;
import com.wstore.utils.HibernateUtil;

public class CategoryDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDAO.class);

	/**
	 *
	 * add new category
	 */
	public void addCategory(Category category){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			//save category object
			session.save(category);

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
	 * Edit category
	 */
	public void updateCategory(Category category) {
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

			//update category detail
			session.update(category);

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
	 * delete category
	 */
	public void deleteCategory(Category category){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			category.setDelete(true);
			session.update(category);

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
	 * get all Category
	 */
	@SuppressWarnings("unchecked")
	public List<Category> findAllCategories() {
		List<Category> categories = new ArrayList<Category>();
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
			Criteria criteria = session.createCriteria(Category.class);
			// To sort records in descening order
			criteria.add(Restrictions.eq("isDelete", false));
			criteria.addOrder(Order.asc("categoryId"));
			//get list from criteria
			categories = criteria.list();

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
		return categories;
	}
	/**
	 * get category by Id
	 */
	public Category findCategoryById(int categoryId){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		Category category = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(Category.class);
			criteria.add(Restrictions.eq("categoryId", categoryId)).add(Restrictions.eq("isDelete",false));
			category = (Category)criteria.uniqueResult();

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
		return category;
	}
}
