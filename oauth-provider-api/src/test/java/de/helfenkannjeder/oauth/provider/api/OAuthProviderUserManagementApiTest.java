package de.helfenkannjeder.oauth.provider.api;

import de.helfenkannjeder.oauth.provider.api.dto.UserRequestDto;
import de.helfenkannjeder.oauth.provider.api.dto.UserResponseDto;
import de.helfenkannjeder.testutils.FeignApiBuilder;
import de.helfenkannjeder.testutils.TestServer;
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
		int port =  TestServer.startHttpServer();
		this.api = FeignApiBuilder.create(OAuthProviderUserManagementApi.class, "http://localhost:" + port);
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