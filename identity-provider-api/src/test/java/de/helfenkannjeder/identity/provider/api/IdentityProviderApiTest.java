package de.helfenkannjeder.identity.provider.api;

import de.helfenkannjeder.identity.provider.api.dto.IdentityDto;
import de.helfenkannjeder.testutils.FeignApiBuilder;
import de.helfenkannjeder.testutils.TestServer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class IdentityProviderApiTest {

	private IdentityProviderApi api;

	@Before
	public void setUp() throws Exception {
		int port = TestServer.startHttpServer();
		this.api = FeignApiBuilder.create(IdentityProviderApi.class, "http://localhost:" + port);
	}

	@Test
	public void create_withUsernameAndPassword_returnsNewId() throws Exception {
		// Arrange
		TestServer.addResource(IdentityProviderApi.CREATE, new TestServer.StringHttpHandler("{\"id\":\"42\"}"));

		// Act
		IdentityDto response = api.create(new IdentityDto().setId(42L));

		// Assert
		assertEquals(Long.valueOf(42L), response.getId());
	}

	@Test
	public void update_withUserAndPassword_doesNotFail() throws Exception {
		// Arrange
		TestServer.addResource(IdentityProviderApi.CREATE.replace("{id}", "42"), new TestServer.StringHttpHandler("{\"id\":\"42\"}"));

		// Act
		IdentityDto response = api.update(42L, new IdentityDto());

		// Assert
		assertEquals(Long.valueOf(42L), response.getId());
	}

	@Test
	public void delete_withId_doesNotFail() throws Exception {
		// Arrange
		TestServer.addResource(IdentityProviderApi.DELETE.replace("{id}", "42"), new TestServer.StringHttpHandler("{\"id\":\"42\"}"));

		// Act
		api.delete(42L);
	}
}