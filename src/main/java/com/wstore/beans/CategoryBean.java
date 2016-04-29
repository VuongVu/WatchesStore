package com.wstore.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.wstore.DAO.CategoryDAO;
import com.wstore.DAO.ProductDAO;
import com.wstore.entities.Category;
import com.wstore.entities.Product;

@ManagedBean(name = "categoryBean")
@SessionScoped
public class CategoryBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Category category = new Category();
	private List<Category> categories = new ArrayList<Category>();
	private List<Product> products = new ArrayList<Product>();

	@SuppressWarnings("unchecked")
	public List<String> getProducts() {
		ProductDAO dao = new ProductDAO();
		products = dao.findAllProducts();
		List<String> list = new ArrayList<>();
		for (int i = 0; i < products.size(); i++) {
			list.add(products.get(i).getProductBrand());
		}
		@SuppressWarnings("rawtypes")
		HashSet hs = new HashSet();
		hs.addAll(list);
		list.clear();
		list.addAll(hs);

		return list;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Category> getCategories() {
		CategoryDAO dao = new CategoryDAO();
		categories = dao.findAllCategories();

		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * save new category
	 */
	public String addCategory(){
		CategoryDAO dao = new CategoryDAO();
		dao.addCategory(this.category);
		//reset data
		category = new Category();
		return "/pages/category/category.jsf?faces-redirect=true";
	}

	/**
	 * get edit action
	 */
	public String editAction(int categoryId){
		CategoryDAO dao = new CategoryDAO();
		this.category = dao.findCategoryById(categoryId);

		return "/pages/category/edit.jsf?faces-redirect=true";
	}

	/**
	 * update categody
	 */
	public String updateCategory() {
		CategoryDAO dao = new CategoryDAO();
		dao.updateCategory(this.category);
		//reset data
		this.category = new Category();

		return "/pages/category/category.jsf?faces-redirect=true";
	}

	/**
	 * delete category
	 */
	public void deleteCategory(Category category) {
		CategoryDAO dao = new CategoryDAO();
		dao.deleteCategory(category);
	}
}
