package de.helfenkannjeder.identity.provider.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.identity.provider.api.dto.IdentityDto;
import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public interface IdentityProviderApi {

	static IdentityProviderApi create(String apiEndpoint, ObjectMapper mapper) {
		return Feign.builder()
				.encoder(new JacksonEncoder(mapper))
				.decoder(new JacksonDecoder(mapper))
				.target(IdentityProviderApi.class, apiEndpoint);
	}

	String CREATE = "/users";
	String GET = "/users/{id}";
	String UPDATE = "/users/{id}";
	String DELETE = "/users/{id}";

	@RequestLine("POST " + CREATE)
	IdentityDto create(IdentityDto identityDto);

	@RequestLine("GET " + GET)
	IdentityDto get(@Param("id") Long id);

	@RequestLine("PUT " + UPDATE)
	IdentityDto update(@Param("id") Long id, IdentityDto identityDto);

	@RequestLine("DELETE " + DELETE)
	void delete(@Param("id") Long id);
}
