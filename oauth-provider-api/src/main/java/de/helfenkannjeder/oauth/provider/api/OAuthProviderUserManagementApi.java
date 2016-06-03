package de.helfenkannjeder.oauth.provider.api;

import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import feign.Param;
import feign.RequestLine;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public interface OAuthProviderUserManagementApi {

    @RequestLine("POST /user/create")
    UserResponseDto create(UserRequestDto userRequestDto);

    @RequestLine("PUT /user/{id}")
    void update(@Param("id") String id, UserRequestDto userRequestDto);

    @RequestLine("DELETE /user/{id}")
    void delete(@Param("id") String id);
}
