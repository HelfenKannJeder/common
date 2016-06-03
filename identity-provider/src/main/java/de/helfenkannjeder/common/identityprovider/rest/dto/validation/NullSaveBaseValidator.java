package de.helfenkannjeder.common.identityprovider.rest.dto.validation;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class NullSaveBaseValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        return null == value || isValidNullSave(value, context);
    }

    protected abstract boolean isValidNullSave(T value, ConstraintValidatorContext context);
}
