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
import com.wstore.DAO.DeliveryDAO;
import com.wstore.DAO.OrderDAO;
import com.wstore.DAO.OrderDetailDAO;
import com.wstore.DAO.ProductDAO;
import com.wstore.entities.Cart;
import com.wstore.entities.Customer;
import com.wstore.entities.Delivery;
import com.wstore.entities.Item;
import com.wstore.entities.Order;
import com.wstore.entities.OrderDetail;
import com.wstore.entities.Product;
import com.wstore.utils.SendMail;


@ManagedBean
@SessionScoped
public class CartBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Cart cart = null;
	private double total_amount=0;
	private int number;
	private boolean delivery_method = false;
	private String[] address = new String[4];
	private Delivery delivery = new Delivery();
	private Delivery checkout_deli = new Delivery();

	
	public Delivery getCheckout_deli() {
		return checkout_deli;
	}

	public void setCheckout_deli(Delivery checkout_deli) {
		this.checkout_deli = checkout_deli;
	}

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

	
	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public String[] getAddress() {
		return address;
	}

	public void setAddress(String[] address) {
		this.address = address;
	}
	
	
	public boolean isDelivery_method() {
		return delivery_method;
	}

	public void setDelivery_method(boolean delivery_method) {
		this.delivery_method = delivery_method;
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
		this.total_amount = (double)Math.round(this.total_amount * 100) / 100;
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

	public String checkOut(String email) {
		int size=0;
		OrderDAO orderDAO = new OrderDAO();
		OrderDetailDAO orderdetailDAO = new OrderDetailDAO();
		ProductDAO dao = new ProductDAO();
		CustomerDAO cusdao=new CustomerDAO();
		boolean flag = false;
		List<Product> lProducts = new ArrayList<Product>();
		Customer customer=cusdao.findCustomerByEmail(email);
		SendMail sendMail = new SendMail();
		
		if(getCart()!=null){
			size = getCart().getItems().size();
			flag = true;
		}

		for (int i = 0; i < size; i++) {
			if (dao.findOneProducts(
					getCart().getItems().get(i).getProduct().getProductId())
					.getProductQuantity() == 0) {
				lProducts.add(getCart().getItems().get(i).getProduct());
				flag = false;
			}
		}


		if (flag) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();

				Order order = new Order();

				if(delivery_method==true){
					order.setTotal_amount(this.total_amount+2);
				}else {
					order.setTotal_amount(this.total_amount);
				}				
				order.setPaid(false);
				order.setOrderDate(dateFormat.format(date));
				order.setCustomer(customer);
				order.setFastDelivery(delivery_method);
				order.setReceiver(checkout_deli.getDeliveryName());
				order.setDelivery_address(checkout_deli.getDeliveryAddress());
				order.setReceiver_phone(checkout_deli.getDeliveryPhone());
				orderDAO.updateOrder(order);

				for (int i = 0; i < cart.getItems().size(); i++) {
					OrderDetail orderDetail = new OrderDetail();

					Item item = cart.getItems().get(i);

					orderDetail.setDiscount(item.getProduct()
							.getProductDiscount());
					orderDetail.setPrice(item.getQuantity() * item.getProduct().getProductPrice() - ((item
							.getQuantity() * item.getProduct()
							.getProductPrice()) * (item.getProduct()
									.getProductDiscount() / 100)));

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
				sendMail.send(order, customer);
				this.cart = null;
				this.total_amount=0;
				this.number=0;
				this.delivery_method=false;
			} catch (Exception e) {
			}
		} else {
			for (int i = 0; i < lProducts.size(); i++) {
				FacesContext.getCurrentInstance().addMessage(
						"checkout",
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Checkout uncomplete, "+
								lProducts.get(i).getProductName()+ " is out of product","Out of products"));
			}
		}
		return "checkout-success.jsf?faces-redirect=true";
	}

	public String saveDelivery(String email){
		DeliveryDAO dao = new DeliveryDAO();
		CustomerDAO cDao = new CustomerDAO();
		
		this.delivery.setCustomer(cDao.findCustomerByEmail(email));
		this.delivery.setDeliveryAddress(address[3]+", "+address[2]+", "+address[1]+", "+address[0]);
		dao.addDelivery(this.delivery);		
		
		this.checkout_deli = this.delivery;
		
		this.delivery = new Delivery();
		address =new String[4];
		return "checkout-shipping.jsf?faces-redirect=true";
	}
	public List<Delivery> findAllDelivery(String email){
		List<Delivery> list=new ArrayList<>();
		DeliveryDAO dao=new DeliveryDAO();
		CustomerDAO cDao = new CustomerDAO();
		list = dao.findAllDeliveries(cDao.findCustomerByEmail(email).getCustomerId());	
		return list;
	}
	
	public String takeDelivery(Delivery delivery){
		this.checkout_deli = delivery;
		return "checkout-shipping.jsf?faces-redirect=true";
	}
}
