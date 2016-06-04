package com.wstore.beans;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.wstore.DAO.CustomerDAO;
import com.wstore.DAO.OrderDAO;
import com.wstore.DAO.OrderDetailDAO;
import com.wstore.DAO.ProductDAO;
import com.wstore.entities.Cart;
import com.wstore.entities.Customer;
import com.wstore.entities.Item;
import com.wstore.entities.Order;
import com.wstore.entities.OrderDetail;
import com.wstore.entities.Product;


@ManagedBean
@SessionScoped
public class CartBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Cart cart = null;
	private double total_amount=0;
	private int number;

	public double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(double total_amount) {
		this.total_amount = total_amount;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	private boolean checkout=false;


	public boolean isCheckout() {
		return checkout;
	}

	public void setCheckout(boolean checkout) {
		this.checkout = checkout;
	}

	public CartBean() {
	}

	/* Them product vao gio hang */
	public void addToCart(Product product,int quantity) {
		//Product currentProduct=this.products.get(this.products.indexOf(product));
		try {
			if (product.getProductQuantity() > 0 && quantity <= product.getProductQuantity()) {

				if (getCart() == null) {
					cart = new Cart();
					getCart().addItem(product, quantity);
					this.number+=1;
					product.setProductQuantity(product.getProductQuantity()-quantity);

				} else {

					if (checkExistProductInCart(product) == -1) {
						getCart().addItem(product, quantity);

						product.setProductQuantity(product.getProductQuantity()-quantity);
						this.number+=1;
						//this.total_amount+=product.getProductPrice()-(product.getProductPrice()*(product.getProductDiscount()/100));
					} else {

						int newQuantity = getCart().getItems()
								.get(checkExistProductInCart(product))
								.getQuantity() + quantity;
						getCart().getItems()
						.get(checkExistProductInCart(product))
						.setQuantity(newQuantity);

						product.setProductQuantity(product.getProductQuantity()-quantity);

					}
				}
				// dao.updateProduct(product);
				totalPrice();

			} else {
				FacesContext.getCurrentInstance().addMessage(
						"cartadd",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								product.getProductName()+" is out of product", "Out of products"));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}

	public void minusFromCart(Product product){
		if(getCart().getItems().get(checkExistProductInCart(product)).getQuantity()>1){
			getCart().getItems().get(checkExistProductInCart(product)).setQuantity(getCart().getItems().get(checkExistProductInCart(product)).getQuantity()-1);
			product.setProductQuantity(product.getProductQuantity()+1);
			totalPrice();
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Minimum Requirement 1 Product", ""));
		}

	}
	public void totalPrice(){
		this.total_amount=0;
		for (int i = 0; i < getCart().getItems().size(); i++) {
			Item item = getCart().getItems().get(i);
			this.total_amount += item.getQuantity() * item.getProduct().getProductPrice() - ((item
					.getQuantity() * item.getProduct()
					.getProductPrice()) * (item.getProduct()
							.getProductDiscount() / 100));
		}
	}

	public String buyNow(Product product,int quantity) throws IOException{
		addToCart(product,quantity);
		return "cart.jsf?faces-redirect=true";
	}

	public void redirectCheckoutSuccess(ActionEvent e) throws IOException{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		if(getCart()!=null){
			context.redirect("checkout-success.jsf");
		}
	}

	/* xoa product khoi gio hang */
	public void deleteFromCart(Product product) {

		product.setProductQuantity(product.getProductQuantity()+getCart().getItems().get(checkExistProductInCart(product)).getQuantity());
		getCart().deleteItem(product.getProductId());

		totalPrice();
		if(getCart().getItems().size()==0){
			this.cart=null;
		}
		this.number-=1;
		setCart(cart);
	}

	public int checkExistProductInCart(Product p) {
		for (int i = 0; i < getCart().getItems().size(); i++) {
			if (getCart().getItems().get(i).getProduct().getProductId() == p
					.getProductId()) {
				return i;
			}
		}

		return -1;
	}

	public void checkOut(Cart cart, String email) {

		int size=0;
		OrderDAO orderDAO = new OrderDAO();
		OrderDetailDAO orderdetailDAO = new OrderDetailDAO();
		ProductDAO dao = new ProductDAO();
		CustomerDAO cusdao=new CustomerDAO();
		boolean flag = true;
		List<Product> lProducts = new ArrayList<Product>();

		if(getCart()!=null){
			size=getCart().getItems().size();
		}else{
			flag=false;
		}

		for (int i = 0; i < size; i++) {
			if (dao.findOneProducts(
					getCart().getItems().get(i).getProduct().getProductId())
					.getProductQuantity() == 0) {
				lProducts.add(getCart().getItems().get(i).getProduct());
				flag = false;
			}
		}
		Customer customer=cusdao.findCustomerByEmail(email);


		if (flag) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();

				Order order = new Order();


				order.setTotal_amount(this.total_amount);
				order.setPaid(false);
				order.setOrderDate(dateFormat.format(date));
				order.setCustomer(customer);
				orderDAO.updateOrder(order);

				for (int i = 0; i < cart.getItems().size(); i++) {
					OrderDetail orderDetail = new OrderDetail();

					Item item = cart.getItems().get(i);

					orderDetail.setDiscount(item.getProduct()
							.getProductDiscount());
					orderDetail.setPrice(item.getQuantity()
							* item.getProduct().getProductPrice());

					orderDetail.setQuantity(item.getQuantity());
					orderDetail.setBillDate(dateFormat.format(date));

					orderDetail.setOrder(order);
					orderDetail.setProduct(item.getProduct());

					orderdetailDAO.updateOrderDetail(orderDetail);

				}
				for(int i=0;i<getCart().getItems().size();i++){
					Item currentItem=getCart().getItems().get(i);
					//currentItem.getProduct().setProductQuantity(currentItem.getProduct().getProductQuantity()-currentItem.getQuantity());
					dao.updateProduct(currentItem.getProduct());
				}
				this.cart = null;
				this.total_amount=0;
				this.number=0;

			} catch (Exception e) {
			}
		} else {
			for (int i = 0; i < lProducts.size(); i++) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								lProducts.get(i).getProductName()+ " is out of product","Out of products"));
			}
		}
	}

}
