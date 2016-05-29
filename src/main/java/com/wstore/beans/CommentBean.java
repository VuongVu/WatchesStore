package com.wstore.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.wstore.DAO.CommentDAO;
import com.wstore.entities.Comment;
import com.wstore.entities.Customer;
import com.wstore.entities.Product;

@ManagedBean
@SessionScoped
public class CommentBean {
	private Comment comment=new Comment();

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public void addComment(Product product,Customer customer){
		CommentDAO dao=new CommentDAO();
		//CustomerDAO customerDAO=new CustomerDAO();

		dao.addComment(this.comment,product,customer);
		this.comment = new Comment();
	}

	public List<Comment> findComment(int productId){
		List<Comment> comments=new ArrayList<>();
		CommentDAO dao=new CommentDAO();
		comments=dao.findAllCommentsByProId(productId);
		return comments;
	}

	public int getRating(Product product){
		CommentDAO dao=new CommentDAO();
		List<Comment> list=new ArrayList<>();
		int total=0;

		try {
			list = dao.findAllCommentsByProId(product.getProductId());
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					total+=list.get(i).getRating();
				}
				return total/list.size();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return 0;
	}

}
