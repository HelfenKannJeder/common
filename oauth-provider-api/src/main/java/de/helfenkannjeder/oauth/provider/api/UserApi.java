package de.helfenkannjeder.oauth.provider.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public interface UserApi {

	static UserApi createUserApi(String apiEndpoint, ObjectMapper mapper) {
		return Feign.builder()
				.encoder(new JacksonEncoder(mapper))
				.decoder(new JacksonDecoder(mapper))
				.target(UserApi.class, apiEndpoint);
	}

	@RequestLine("POST /user/create")
	UserResponseDto create(UserRequestDto userRequestDto);

	@RequestLine("PUT /user/{id}")
	void update(@Param("id") String id, UserRequestDto userRequestDto);

	@RequestLine("DELETE /user/{id}")
	void delete(@Param("id") String id);
}
