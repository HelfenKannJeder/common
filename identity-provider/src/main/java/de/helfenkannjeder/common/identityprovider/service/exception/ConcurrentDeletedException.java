package de.helfenkannjeder.common.identityprovider.service.exception;

public class ConcurrentDeletedException extends RuntimeException {

    public ConcurrentDeletedException(Object id) {
        super(String.format("Object with ID %s was deleted competitive", id));
    }
}
