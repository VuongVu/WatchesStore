package com.wstore.service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.wstore.DAO.SupplierDAO;
import com.wstore.entities.Supplier;



@FacesConverter("supplierConverter")
public class SupplierConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Supplier supplier = null;
		SupplierDAO dao = new SupplierDAO();

		try {
			supplier = dao.findSupplierById(Integer.parseInt(value));
		} catch (Exception e) {
			throw new ConverterException("Can not convert!" + e.getMessage());
		}

		return supplier;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		try {
			if(object == null) {
				return null;
			}else {
				return String.valueOf(((Supplier) object).getSupplierId()).toString();
			}
		} catch (Exception ex) {
			throw new ConverterException("Can not convert to string " + ex.getMessage());
		}
	}

}
