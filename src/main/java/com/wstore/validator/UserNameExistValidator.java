package com.wstore.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wstore.service.UserNameService;



public class UserNameExistValidator implements ConstraintValidator<UserNameExistsConstraint, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserNameExistValidator.class);

	@Override
	public void initialize(UserNameExistsConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) {
			return true;
		}
		try {
			if(UserNameService.checkUserName(value)) {
				return false;
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return true;
	}

}
