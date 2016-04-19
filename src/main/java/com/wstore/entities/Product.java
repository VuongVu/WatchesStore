package com.wstore.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product implements	Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productId")
	private Integer productId;

	@Size(min = 1, max = 30, message = "Product Name should be of length from 1 to 30 charst")
	@Column(name = "productName")
	private String productName;

	@Size(min = 1, max = 30, message = "Product Brand should be of length from 1 to 30 charst")
	@Column(name = "productBrand")
	private String productBrand;

	@Column(name = "productPrice")
	private double productPrice;

	@Column(name = "productQuantity")
	private int productQuantity;

	@Column(name = "productDiscount")
	private double productDiscount;

	@Column(name = "productRanking")
	private double productRanking;

	@Lob
	@Column(name = "productImage")
	private byte[] productImage;

	@Size(max = 50, message = "Product Description should be max of length 50 charst")
	@Column(name = "productDescription")
	private String productDescription;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "supplierId")
	private Supplier supplier;

	@OneToMany(mappedBy = "product")
	private Set<OrderDetail> orderDetails;

	@Column(name="isDelete")
	private boolean isDelete;


	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Product() {

	}

	public Product(String productName, String productBrand,
			double productPrice, int productQuantity, double productDiscount,
			double productRanking, byte[] productImage,
			String productDescription, Category category, Supplier supplier) {
		super();
		this.productName = productName;
		this.productBrand = productBrand;
		this.productPrice = productPrice;
		this.productQuantity = productQuantity;
		this.productDiscount = productDiscount;
		this.productRanking = productRanking;
		this.productImage = productImage;
		this.productDescription = productDescription;
		this.category = category;
		this.supplier = supplier;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public double getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(double productDiscount) {
		this.productDiscount = productDiscount;
	}

	public double getProductRanking() {
		return productRanking;
	}

	public void setProductRanking(double productRanking) {
		this.productRanking = productRanking;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (productId != null ? productId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Product)) {
			return false;
		}
		Product product = (Product) object;
		if ((this.productId == null && product.productId != null) || (this.productId != null && !this.productId.equals(product.productId))) {
			return false;
		}
		return true;
	}
}
