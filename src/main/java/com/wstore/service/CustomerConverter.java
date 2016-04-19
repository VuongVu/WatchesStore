package com.wstore.service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.wstore.DAO.CustomerDAO;
import com.wstore.entities.Customer;



@FacesConverter("customerConverter")
public class CustomerConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Customer customer = null;
		CustomerDAO dao = new CustomerDAO();

		try {
			customer = dao.findCustomerById(Integer.parseInt(value));
		} catch (Exception e) {
			throw new ConverterException("Can not convert!" + e.getMessage());
		}

		return customer;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			if(value == null) {
				return null;
			}else {
				return String.valueOf(((Customer) value).getCustomerId()).toString();
			}
		} catch (Exception ex) {
			throw new ConverterException("Can not convert to string " + ex.getMessage());
		}
	}

}
