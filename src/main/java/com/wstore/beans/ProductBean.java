package com.wstore.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;

import com.wstore.DAO.CategoryDAO;
import com.wstore.DAO.ProductDAO;
import com.wstore.DAO.SupplierDAO;
import com.wstore.entities.Category;
import com.wstore.entities.Product;
import com.wstore.entities.Supplier;
@ManagedBean(name="productBean")
@SessionScoped
public class ProductBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Product product = new Product();
	private Product productDetail = new Product();
	private List<SelectItem> listCategories = new ArrayList<SelectItem>();
	private List<SelectItem> listSuppliers = new ArrayList<SelectItem>();
	private List<Product> listProductByBrand = new ArrayList<Product>();
	private List<Product> listSearch = new ArrayList<Product>();
	private List<Product> listFilter = new ArrayList<>();
	private UploadedFile image;
	private String productBrand="%";
	private double productPrice=100000;
	private String productProperty="isAll";
	private int categoryId;
	private int sort = 0;
	private String searchString;
	private Product product_detail = new Product();


	public Product getProduct_detail() {
		return product_detail;
	}

	public void setProduct_detail(Product product_detail) {
		this.product_detail = product_detail;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public List<Product> getListFilter() {
		return listFilter;
	}

	public void setListFilter(List<Product> listFilter) {
		this.listFilter = listFilter;
	}

	public List<Product> getListProductByBrand() {
		return listProductByBrand;
	}

	public void setListProductByBrand(List<Product> listProductByBrand) {
		this.listProductByBrand = listProductByBrand;
	}

	public List<Product> getListSearch() {
		return listSearch;
	}

	public void setListSearch(List<Product> listSearch) {
		this.listSearch = listSearch;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductProperty() {
		return productProperty;
	}

	public void setProductProperty(String productProperty) {
		this.productProperty = productProperty;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public Product getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(Product productDetail) {
		this.productDetail = productDetail;
	}

	public List<SelectItem> getListCategories() {
		CategoryDAO dao = new CategoryDAO();
		listCategories.clear();
		for (Category c : dao.findAllCategories()) {
			listCategories.add(new SelectItem(c, c.getCategoryName()));
		}

		return listCategories;
	}

	public List<String> getListProductBrand(){
		List<String> list=new ArrayList<>();
		HashSet<String> hashSet=new HashSet<>();
		ProductDAO dao=new ProductDAO();

		for (int i = 0; i < dao.findAllProducts().size(); i++) {
			hashSet.add(dao.findAllProducts().get(i).getProductBrand());
		}
		list.addAll(hashSet);

		return list;
	}
	public void setListCategories(List<SelectItem> listCategories) {
		this.listCategories = listCategories;
	}

	/**
	 * get list supplier
	 *
	 * @return list
	 */
	public List<SelectItem> getListSuppliers() {
		SupplierDAO dao = new SupplierDAO();
		listSuppliers.clear();
		for (Supplier s : dao.findAllSuppliers()) {
			listSuppliers.add(new SelectItem(s, s.getCompanyName()));
		}

		return listSuppliers;
	}

	public void setListSuppliers(List<SelectItem> listSuppliers) {
		this.listSuppliers = listSuppliers;
	}

	public UploadedFile getImage() {
		return image;
	}

	public void setImage(UploadedFile image) {
		this.image = image;
	}

	/**
	 * add new product
	 *
	 * @throws IOException
	 */
	public String addProduct() {
		ProductDAO dao = new ProductDAO();
		if (image != null) {
			// get content of image
			byte[] imageData = image.getContents();
			this.product.setProductImage(imageData);
		}
		dao.addProduct(this.product);
		product = new Product();

		return "/pages/product/product.jsf?faces-redirect=true";
	}

	/**
	 * get edit action
	 */
	public String editAction(Product product) {
		this.product = product;
		return "/pages/product/edit.jsf?faces-redirect=true";
	}

	/**
	 * update product
	 */
	public String updateProduct() {
		ProductDAO dao = new ProductDAO();
		if (image != null) {
			// get content of image
			byte[] imageData = image.getContents();
			this.product.setProductImage(imageData);
		}
		dao.updateProduct(this.product);
		product = new Product();

		return "/pages/product/product.jsf?faces-redirect=true";
	}

	/**
	 * delete product
	 */
	public void deleteProduct(Product product) {
		ProductDAO dao = new ProductDAO();
		dao.deleteProduct(product);
	}

	public String getProductDetail(Product product) {
		this.product_detail = product;
		return "product-detail.jsf?faces-redirect=true";
	}

	/**
	 * search product
	 *
	 * @return
	 */

	public List<Product> listProducts() {
		ProductDAO dao = new ProductDAO();
		List<Product> list = new ArrayList<Product>();
		list=dao.findAllProducts();
		return list;
	}

	public List<Product> listNewProduct(){
		ProductDAO dao=new ProductDAO();
		List<Product> list=dao.findNewProducts();
		return list;
	}
	public List<Product> listSaleProduct(){
		ProductDAO dao=new ProductDAO();
		List<Product> list=dao.findSaleProducts();
		return list;
	}
	public List<Product> listBestProduct(){
		ProductDAO dao=new ProductDAO();
		List<Product> list = dao.findBestProducts();
		return list;
	}


	public String getCategoryId(int categoryId) {
		ProductDAO dao=new ProductDAO();
		this.categoryId=categoryId;
		this.listFilter=dao.findProductByCatId(categoryId);
		return "/shop-grid.jsf?faces-redirect=true";
	}


	public List<Product> productByBrand(Product product) {
		List<Product> list=new ArrayList<>();
		ProductDAO dao = new ProductDAO();
		try {
			list = dao.findProductByBrand(product.getProductBrand());
			list.remove(product);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sada"+ e);
		}
		productDetail = product;
		return list;
	}
	public List<Product> productByCategory(Product product) {
		List<Product> list=new ArrayList<>();
		ProductDAO dao = new ProductDAO();
		try {
			list = dao.findProductByCatId(product.getCategory().getCategoryId());
			list.remove(product);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return list;
	}


	public void processValueChange(){
		// TODO Auto-generated method stub
		ProductDAO dao=new ProductDAO();
		try {
			this.listFilter=dao.filter(this.categoryId, this.productBrand, this.productPrice, this.productProperty, this.sort);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public String search(){
		ProductDAO dao=new ProductDAO();
		if (searchString != "") {
			this.listSearch = dao.search(this.searchString);
			this.searchString = "";
			return "/search.jsf?faces-redirect=true";
		}
		return null;
	}

	public void takeModalProduct(int id){
		//Product product = new Product();
		ProductDAO dao=new ProductDAO();
		productDetail = dao.findOneProducts(id);
	}
}
