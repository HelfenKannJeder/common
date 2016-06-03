package de.helfenkannjeder.oauth.provider.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public interface OAuthProviderUserManagementApi {

	static OAuthProviderUserManagementApi create(String apiEndpoint, ObjectMapper mapper) {
		return Feign.builder()
				.encoder(new JacksonEncoder(mapper))
				.decoder(new JacksonDecoder(mapper))
				.target(OAuthProviderUserManagementApi.class, apiEndpoint);
	}

	String CREATE = "/users";
	String UPDATE = "/users/{id}";
	String DELETE = "/users/{id}";

	@RequestLine("POST " + CREATE)
	UserResponseDto create(UserRequestDto userRequestDto);

	@RequestLine("PUT " + UPDATE)
	void update(@Param("id") String id, UserRequestDto userRequestDto);

	@RequestLine("DELETE " + DELETE)
	void delete(@Param("id") String id);
}
