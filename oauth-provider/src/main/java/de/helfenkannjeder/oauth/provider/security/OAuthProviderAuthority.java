package de.helfenkannjeder.oauth.provider.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public enum OAuthProviderAuthority implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }
}
