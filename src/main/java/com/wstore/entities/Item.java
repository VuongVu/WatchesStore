package com.wstore.entities;

public class Item {
	private Product product = null;
	private int quantity = 0;

	public Item(Product product, int quantity) {

		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
