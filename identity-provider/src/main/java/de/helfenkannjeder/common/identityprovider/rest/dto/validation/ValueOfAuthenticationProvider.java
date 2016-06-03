package de.helfenkannjeder.common.identityprovider.rest.dto.validation;


import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AuthenticationProviderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface ValueOfAuthenticationProvider {
	String message() default "";
}
