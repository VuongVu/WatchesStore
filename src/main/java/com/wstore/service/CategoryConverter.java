package com.wstore.service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.wstore.DAO.CategoryDAO;
import com.wstore.entities.Category;



@FacesConverter("categoryConverter")
public class CategoryConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Category category = null;
		CategoryDAO dao = new CategoryDAO();

		try {
			category = dao.findCategoryById(Integer.parseInt(value));
		} catch (Exception e) {
			throw new ConverterException("Can not convert!" + e.getMessage());
		}

		return category;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			if(value == null) {
				return null;
			}else {
				return String.valueOf(((Category) value).getCategoryId()).toString();
			}
		} catch (Exception ex) {
			throw new ConverterException("Can not convert to string " + ex.getMessage());
		}
	}

}
