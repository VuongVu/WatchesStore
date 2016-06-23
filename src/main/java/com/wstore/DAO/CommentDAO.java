package com.wstore.DAO;

import java.util.ArrayList;
import java.util.Date;
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

import com.wstore.entities.Comment;
import com.wstore.entities.Customer;
import com.wstore.entities.Product;
import com.wstore.utils.HibernateUtil;

public class CommentDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentDAO.class);
	
	public void addComment(Comment comment,Product product,Customer customer){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		Date date = new Date();

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			comment.setProduct(product);
			comment.setCustomer(customer);
			comment.setIsdel(false);
			comment.setDate_create(date);
			//save comment object
			if(isExist(product, customer)!=null){
				session.update(comment);
			}else {
				session.save(comment);
			}
			

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
	public Comment isExist(Product product,Customer customer){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;
		Comment comment = null;
		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			//comment.setDelete(true);
			Criteria criteria = session.createCriteria(Comment.class);
			criteria.add(Restrictions.eq("id.customer", customer)).add(Restrictions.eq("id.product", product));
			
			comment=(Comment) criteria.uniqueResult();
			

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
		return comment;
	}
	
	/**
	 * delete comment
	 */
	public void deleteComment(Comment comment){
		SessionFactory sessionFactory = null;
		Session session = null;
		Transaction tx = null;

		try{
			//get Session object
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			// Starting Transaction
			tx = session.beginTransaction();
			//comment.setDelete(true);
			comment.setIsdel(true);
			session.update(comment);

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
	 * get all Comment
	 */
	
	@SuppressWarnings("unchecked")
	public List<Comment> findAllCommentsByProId(int productId) {
		List<Comment> comments = new ArrayList<Comment>();
		List<Comment> list=new ArrayList<>();
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
			Criteria criteria = session.createCriteria(Comment.class);
//			// To sort records in descening order
//			
			
			comments=criteria.list();
			for (int i = 0; i < comments.size(); i++) {
				if(comments.get(i).getProduct().getProductId()==productId && comments.get(i).isIsdel()==false){
					list.add(comments.get(i));
				}
			}
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
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> findAllComments() {
		List<Comment> comments = new ArrayList<Comment>();
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
			Criteria criteria = session.createCriteria(Comment.class);
//			// To sort records in descening order
//			
			criteria.add(Restrictions.eq("isdel", false));
			criteria.addOrder(Order.asc("date_create"));
			comments=criteria.list();
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
		return comments;
	}
	
}
