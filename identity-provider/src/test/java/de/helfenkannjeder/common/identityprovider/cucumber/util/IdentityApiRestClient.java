package de.helfenkannjeder.common.identityprovider.cucumber.util;

import de.helfenkannjeder.common.identityprovider.rest.dto.IdentityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IdentityApiRestClient {

    private final RestTemplate restTemplate;
    private final String serviceBaseUrl;

    @Autowired
    public IdentityApiRestClient(RestTemplate restTemplate, @Value("${identityProvider.baseUrl}") String serviceBaseUrl) {
        this.restTemplate = restTemplate;
        this.serviceBaseUrl = serviceBaseUrl;
    }

    public ResponseEntity<IdentityDto> createIdentity(IdentityDto identityDto) {
        return restTemplate.exchange(getIdentitiesUrl(), HttpMethod.POST, createHttpEntity(identityDto), IdentityDto.class);
    }

    public ResponseEntity<IdentityDto> getIdentity(Long id) {
        return restTemplate.exchange(getIdentitiesUrl(), HttpMethod.GET, null, IdentityDto.class, id);
    }

    public ResponseEntity<IdentityDto> updateIdentity(IdentityDto identityDto) {
        return restTemplate.exchange(getIdentityUrl(), HttpMethod.PUT, createHttpEntity(identityDto), IdentityDto.class, identityDto.getId());
    }

    public HttpStatus deleteIdentity(Long id) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(getIdentityUrl(), HttpMethod.DELETE, null, Void.class, id);

        return responseEntity.getStatusCode();
    }

    private String getIdentitiesUrl() {
        return serviceBaseUrl + "/identities";
    }

    private String getIdentityUrl() {
        return getIdentitiesUrl() + "/{id}";
    }

    private <T> HttpEntity<T> createHttpEntity(T body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, httpHeaders);
    }
}
