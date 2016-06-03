package de.helfenkannjeder.common.identityprovider.rest.dto.validation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AuthenticationProviderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAuthenticationProvider {
	String message() default "authenticationProvider.not.valid";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
