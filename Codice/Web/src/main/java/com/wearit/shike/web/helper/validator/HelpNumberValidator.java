package com.wearit.shike.web.helper.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.wearit.shike.web.model.user.HelpNumber;

public class HelpNumberValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return HelpNumber.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "required.description");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "required.number");

		HelpNumber var = (HelpNumber) target;

		if(var.getNumber().length() != 0 && !(var.getNumber().matches("[0-9]{3,10}"))) {
			errors.rejectValue("number", "notmatch.numbNotValid");
		}
	}

}