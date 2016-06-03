package de.helfenkannjeder.oauth.provider.api;

import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import feign.Param;
import feign.RequestLine;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public interface OAuthProviderUserManagementApi {

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
