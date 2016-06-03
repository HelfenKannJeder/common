package de.helfenkannjeder.common.identityprovider.rest.dto.validation;

import de.helfenkannjeder.common.identityprovider.domain.AuthenticationProvider;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AuthenticationProviderValidator implements ConstraintValidator<ValueOfAuthenticationProvider, String> {

	public void initialize(ValueOfAuthenticationProvider constraint) {
	}

	public boolean isValid(String obj, ConstraintValidatorContext context) {
		if (obj == null) {
			return true;
		}

		return AuthenticationProvider.API_NAMES.contains(obj);
	}
}
