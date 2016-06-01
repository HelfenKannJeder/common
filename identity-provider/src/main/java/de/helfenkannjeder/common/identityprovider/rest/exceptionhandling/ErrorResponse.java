package de.helfenkannjeder.common.identityprovider.rest.exceptionhandling;

import java.util.List;

public class ErrorResponse {
    public String incidentId;
    public String description;
    public List<ClientError> clientErrors;
}
