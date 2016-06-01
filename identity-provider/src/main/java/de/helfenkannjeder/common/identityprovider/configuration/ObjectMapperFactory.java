package de.helfenkannjeder.common.identityprovider.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {
    public static ObjectMapper objectMapperForRestEndpoint() {
        return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
