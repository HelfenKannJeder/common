package de.helfenkannjeder.oauth.provider.rest;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@Transactional
@DirtiesContext
public class UserControllerTest extends AbstractOAuthControllerTest {

    @Test
    public void currentUser_withNormalUser_expectUsername() throws Exception {
        // Act + Assert
        this.mockMvc.perform(get("/user/information")
                .header("Authorization", getAuthorizationDefaultUser())
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.username").value(DEFAULT_USER));
    }

    @Test
    public void currentUser_withAdmin_expectNoUsername() throws Exception {
        // Act + Assert
        this.mockMvc.perform(get("/user/information")
                .header("Authorization", getAuthorizationAdmin())
                .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.username").value("oauth-provider-admin"));
    }
}