package de.helfenkannjeder.common.identityprovider.rest.dto.validation;

import de.helfenkannjeder.common.identityprovider.domain.AuthenticationProvider;

import javax.validation.ConstraintValidatorContext;

public class AuthenticationProviderValidator extends NullSaveBaseValidator<ValidAuthenticationProvider, String> {

	@Override
	public void initialize(ValidAuthenticationProvider constraintAnnotation) {

	}

	@Override
	protected boolean isValidNullSave(String value, ConstraintValidatorContext context) {
		return AuthenticationProvider.API_NAMES.contains(value.toUpperCase());
	}
}
