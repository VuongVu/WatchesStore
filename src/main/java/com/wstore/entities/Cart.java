package com.wstore.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Item> items = null;

	public Cart() {
		items = null;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addItem(Product product, int quantity) {
		if (items == null) {
			items = new ArrayList<Item>();
		}
		items.add(new Item(product, quantity));
	}

	public void deleteItem(int productId) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getProduct().getProductId() == productId) {
				items.remove(i);
			}
		}
	}

}
