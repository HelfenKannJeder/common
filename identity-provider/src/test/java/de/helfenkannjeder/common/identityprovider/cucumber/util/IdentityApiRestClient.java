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
    public IdentityApiRestClient(RestTemplate restTemplate, @Value("${come2help.baseUrl}") String serviceBaseUrl) {
        this.restTemplate = restTemplate;
        this.serviceBaseUrl = serviceBaseUrl;
    }

    public ResponseEntity<IdentityDto> createVolunteer(IdentityDto identityDto) {
        return restTemplate.exchange(getVolunteersUrl(), HttpMethod.POST, createHttpEntity(identityDto), IdentityDto.class);
    }

    public ResponseEntity<IdentityDto> getVolunteer(Long id) {
        return restTemplate.exchange(getVolunteersUrl(), HttpMethod.GET, null, IdentityDto.class, id);
    }

    /*
    public ResponseEntity<UserDto> updateVolunteer(IdentityDto identityDto) {
        return restTemplate.exchange(getVolunteerUrl(), HttpMethod.PUT, createHttpEntity(identityDto), IdentityDto.class, identityDto.getId());
    }
    */

    public HttpStatus deleteVolunteer(Long id) {
        ResponseEntity<Void> responseEntity = restTemplate.exchange(getVolunteerUrl(), HttpMethod.DELETE, null, Void.class, id);

        return responseEntity.getStatusCode();
    }

    private String getVolunteersUrl() {
        return serviceBaseUrl + "/volunteers";
    }

    private String getVolunteerUrl() {
        return getVolunteersUrl() + "/{id}";
    }

    private <T> HttpEntity<T> createHttpEntity(T body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, httpHeaders);
    }
}
