package com.wstore.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wstore.service.EmailService;



public class EmailExistValidator implements ConstraintValidator<EmailExistsConstraint, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailExistValidator.class);

	@Override
	public void initialize(EmailExistsConstraint constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) {
			return true;
		}
		try {
			if(EmailService.checkEmail(value)) {
				return false;
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		return true;
	}
}