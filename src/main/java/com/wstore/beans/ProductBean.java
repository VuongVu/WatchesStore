package com.wstore.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;

import com.wstore.DAO.CategoryDAO;
import com.wstore.DAO.ProductDAO;
import com.wstore.DAO.SupplierDAO;
import com.wstore.entities.Category;
import com.wstore.entities.Product;
import com.wstore.entities.Supplier;

@ManagedBean(name = "productBean")
@SessionScoped
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Product product = new Product();
	private List<Product> products = new ArrayList<Product>();

	private List<SelectItem> listCategories = new ArrayList<SelectItem>();
	private List<SelectItem> listSuppliers = new ArrayList<SelectItem>();
	private List<Product> listProductByBrand = new ArrayList<Product>();
	private List<Product> listProductByCate = new ArrayList<Product>();
	private UploadedFile image;
	private String searchString = "";
	private boolean filterBrand = false;
	private boolean filterCate = false;
	private boolean search = true;

	private String brand;

	private int categoryId = 0;

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public List<Product> getListProductByBrand() {
		return listProductByBrand;
	}

	public void setListProductByBrand(List<Product> listProductByBrand) {
		this.listProductByBrand = listProductByBrand;
	}

	public List<Product> getListProductByCate() {
		return listProductByCate;
	}

	public void setListProductByCate(List<Product> listProductByCate) {
		this.listProductByCate = listProductByCate;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isFilterBrand() {
		return filterBrand;
	}

	public void setFilterBrand(boolean filterBrand) {
		this.filterBrand = filterBrand;
	}

	public boolean isFilterCate() {
		return filterCate;
	}

	public void setFilterCate(boolean filterCate) {
		this.filterCate = filterCate;
	}

	/**
	 * get list product
	 *
	 * @return list
	 */
	public List<Product> getProducts() {
		ProductDAO dao = new ProductDAO();
		products = dao.findAllProducts();

		return products;
	}

	public List<String> listBrands() {
		ProductDAO dao = new ProductDAO();
		List<Product> list = new ArrayList<Product>();
		List<String> list2 = new ArrayList<String>();
		Set<String> hs = new HashSet<String>();

		list = dao.findAllProducts();

		for (Product p : list) {
			list2.add(p.getProductBrand());
		}
		hs.addAll(list2);
		list2.clear();
		list2.addAll(hs);

		return list2;
	}

	/**
	 * get list category
	 *
	 * @return list
	 */
	public List<SelectItem> getListCategories() {
		CategoryDAO dao = new CategoryDAO();
		listCategories.clear();
		for (Category c : dao.findAllCategories()) {
			listCategories.add(new SelectItem(c, c.getCategoryName()));
		}

		return listCategories;
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
		this.product = product;
		return "/productDetail.jsf?faces-redirect=true";
	}

	/**
	 * search product
	 *
	 * @return
	 */
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public List<Product> listProducts() {
		filterBrand = false;
		filterCate = false;
		search = true;
		ProductDAO dao = new ProductDAO();
		List<Product> list = new ArrayList<Product>();
		if (this.searchString == null || this.searchString=="") {
			if (this.categoryId != 0) {
				list = dao.findProductByCatId(this.categoryId);
				this.categoryId=0;
			} else {
				list = dao.findAllProducts();
				this.categoryId=0;
			}

		} else{
			if(this.categoryId==0){
				for (Product p : dao.findAllProducts()) {
					if (p.getProductName().toLowerCase()
							.contains(this.searchString.toLowerCase())) {
						list.add(p);
					}
				}
			}
			else {
				list = dao.findProductByCatId(this.categoryId);
				this.categoryId=0;
			}
		}
		return list;
	}

	public List<Product> getSameProduct() {
		List<Product> list = new ArrayList<Product>();
		List<Product> list2 = new ArrayList<Product>();
		int size;
		ProductDAO dao = new ProductDAO();
		if (!this.product.equals(null)) {
			for (Product product : dao.findAllProducts()) {
				if (product.getProductBrand().equals(
						this.product.getProductBrand())) {
					list.add(product);
				}
			}
			Random random = new Random();

			if (list.size() > 3) {
				size = 3;
			} else {
				size = list.size();
			}
			for (int i = 0; i < size; i++) {
				list2.add(list.get(random.nextInt(list.size())));
				int j = 0;
				while (j < i) {
					if (list2.get(j) == list2.get(i)) {
						list2.add(list.get(random.nextInt(list.size())));
						j = 0;
					} else {
						j++;
					}
				}
			}
			return list2;
		} else {
			return null;
		}
	}

	public String getCategoryId(int categoryId) {
		this.categoryId = categoryId;
		this.search = true;
		return "/products.jsf?faces-redirect=true";
	}

	public String getCategoryIdFromIndex(long categoryId) {
		this.categoryId = (int)categoryId;
		this.search = true;
		return "/products.jsf?faces-redirect=true";
	}

	/**
	 * filter Product by brand
	 *
	 * @param e
	 */
	public void filterByBrand(ValueChangeEvent e) {
		filterBrand = true;
		filterCate = false;
		search = false;
		ProductDAO dao = new ProductDAO();
		this.listProductByBrand = new ArrayList<Product>();
		for (Product product : dao.findAllProducts()) {
			if (product.getProductBrand().equalsIgnoreCase(
					e.getNewValue().toString())) {
				this.listProductByBrand.add(product);
			}
		}
	}

	/**
	 * filter Product by category
	 *
	 * @param e
	 */
	public void filterByCategory(ValueChangeEvent e) {
		filterCate = true;
		filterBrand = false;
		search = false;
		ProductDAO dao = new ProductDAO();
		this.listProductByCate = new ArrayList<Product>();
		if (e.getNewValue().equals("all")) {
			this.listProductByCate = dao.findAllProducts();
		} else {
			for (Product product : dao.findAllProducts()) {
				if (product.getCategory().getCategoryName()
						.equalsIgnoreCase(e.getNewValue().toString())) {
					this.listProductByCate.add(product);
				}
			}
		}

	}
}
