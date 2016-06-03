package de.helfenkannjeder.oauth.provider.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.oauth.provider.api.util.TestServer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class OAuthProviderUserManagementApiTest {

	private OAuthProviderUserManagementApi api;

	@Before
	public void setUp() throws Exception {
		TestServer.startHttpServer();
		int port = TestServer.getPort();

		ObjectMapper mapper = new ObjectMapper()
				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.configure(SerializationFeature.INDENT_OUTPUT, true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		this.api = OAuthProviderUserManagementApi.create("http://localhost:" + port, mapper);
	}

	@Test
	public void create_withUsernameAndPassword_returnsNewId() throws Exception {
		// Arrange
		TestServer.addResource(OAuthProviderUserManagementApi.CREATE, new TestServer.StringHttpHandler("{\"id\":\"myUserId\"}"));

		// Act
		UserResponseDto response = api.create(new UserRequestDto("myUser", "myPassword"));

		// Assert
		assertEquals("myUserId", response.getId());
	}

	@Test
	public void update_withUserAndPassword_doesNotFail() throws Exception {
		// Arrange
		TestServer.addResource(OAuthProviderUserManagementApi.CREATE.replace("{id}", "myUserId"), new TestServer.NoContentHttpHandler());

		// Act
		api.update("myUserId", new UserRequestDto("myUser", "myPassword"));
	}

	@Test
	public void delete_withId_doesNotFail() throws Exception {
		// Arrange
		TestServer.addResource(OAuthProviderUserManagementApi.DELETE.replace("{id}", "myUserId"), new TestServer.NoContentHttpHandler());

		// Act
		api.delete("myUserId");
	}
}