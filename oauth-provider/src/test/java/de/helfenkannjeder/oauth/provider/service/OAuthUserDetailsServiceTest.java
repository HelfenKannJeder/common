package de.helfenkannjeder.oauth.provider.service;

import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import de.helfenkannjeder.oauth.provider.domain.repository.OAuthUserRepository;
import de.helfenkannjeder.oauth.provider.security.OAuthUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@RunWith(MockitoJUnitRunner.class)
public class OAuthUserDetailsServiceTest {

    private OAuthUserDetailsService oAuthUserDetailsService;

    @Mock
    private OAuthUserRepository oAuthUserRepository;

    @Before
    public void setUp() throws Exception {
        when(oAuthUserRepository.findOneByUsername("my-user")).thenReturn(new OAuthUser("my-user", "$2a$10$mXEdVKm16/vj/JyE.MgQ..UBa0p4rF1JYeGvLzvOJykact6UPVRx."));
        oAuthUserDetailsService = new OAuthUserDetailsService(oAuthUserRepository);
    }

    @Test
    public void loadUserByUsername_withMockedUser_verifyUserDetails() throws Exception {
        // Arrange

        // Act
        UserDetails userDetails = oAuthUserDetailsService.loadUserByUsername("my-user");

        // Assert
        assertNotNull(userDetails);
        assertEquals("my-user", userDetails.getUsername());
        assertEquals("$2a$10$mXEdVKm16/vj/JyE.MgQ..UBa0p4rF1JYeGvLzvOJykact6UPVRx.", userDetails.getPassword());
    }
}