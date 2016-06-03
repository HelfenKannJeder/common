package de.helfenkannjeder.oauth.provider.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.oauth.provider.api.util.TestServer;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class UserApiTest {

    private UserApi userApi;

    @Before
    public void setUp() throws Exception {
        TestServer.startHttpServer();
        int port = TestServer.getPort();

        ObjectMapper mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.userApi = Feign.builder()
                .encoder(new JacksonEncoder(mapper))
                .decoder(new JacksonDecoder(mapper))
                .target(UserApi.class, "http://localhost:" + port);
    }

    @Test
    public void create_withUsernameAndPassword_returnsNewId() throws Exception {
        // Arrange
        TestServer.addResource("/user/create", new TestServer.StringHttpHandler("{\"id\":\"myUserId\"}"));

        // Act
        UserResponseDto response = userApi.create(new UserRequestDto("myUser", "myPassword"));

        // Assert
        assertEquals("myUserId", response.getId());
    }

    @Test
    public void update_withUserAndPassword_doesNotFail() throws Exception {
        // Arrange
        TestServer.addResource("/user/myUserId", new TestServer.NoContentHttpHandler());

        // Act
        userApi.update("myUserId", new UserRequestDto("myUser", "myPassword"));
    }

    @Test
    public void delete_withId_doesNotFail() throws Exception {
        // Arrange
        TestServer.addResource("/user/myUserId", new TestServer.NoContentHttpHandler());

        // Act
        userApi.delete("myUserId");
    }
}