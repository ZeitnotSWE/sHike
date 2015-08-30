package com.wearit.shike.web.helper.validator;

import java.util.Date;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.wearit.shike.web.model.user.CommonAccount;

public class CommonAccountValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CommonAccount.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "required.emailAddress");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "required.firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "required.lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "height", "required.height");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "weight", "required.weight");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "required.birthDate");

		CommonAccount var = (CommonAccount) target;

		checkPassword(var.getPasswordHash(), var.getPasswordHashRepeat(), errors);

		if(var.getBirthDate() != null && !var.getBirthDate().before(new Date())) {
			errors.rejectValue("birthDate", "notmatch.birthDateMax");
		}
	}

	public static void checkPassword(String pass1, String pass2, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordHash", "required.passwordHash");

		if(pass1 != null && pass2 != null) {
			if(!pass1.matches("^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$")) {
				errors.rejectValue("passwordHash", "notmatch.passwordPattern");
			}

			if(!pass1.equals(pass2)) {
				errors.rejectValue("passwordHashRepeat", "notmatch.passwordHashRepeat");
			}
		}
	}

}
