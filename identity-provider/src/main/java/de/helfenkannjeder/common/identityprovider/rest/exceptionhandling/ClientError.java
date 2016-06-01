package de.helfenkannjeder.common.identityprovider.rest.exceptionhandling;

import de.helfenkannjeder.common.identityprovider.service.exception.DataError;
import org.springframework.validation.FieldError;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class ClientError {

    public static ClientError fromFieldError(FieldError fieldError) {
        ClientError clientError = new ClientError();
        clientError.path = fieldError.getField();
        clientError.code = fieldError.getDefaultMessage();
        clientError.value = fieldError.getRejectedValue();

        return clientError;
    }

    public static ClientError fromMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException mismatchException) {
        ClientError clientError = new ClientError();
        clientError.path = mismatchException.getName();
        clientError.code = "Type mismatch of parameter. Should be " + mismatchException.getRequiredType().getSimpleName();
        clientError.value = mismatchException.getValue();

        return clientError;
    }

    public static ClientError fromDataError(DataError dataError) {
        ClientError clientError = new ClientError();
        clientError.code = dataError.code;
        clientError.value = dataError.value;

        return clientError;
    }

    public String path;
    public String code;
    public Object value;
}
