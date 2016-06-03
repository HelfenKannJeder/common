package de.helfenkannjeder.oauth.provider.service;

import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import de.helfenkannjeder.oauth.provider.domain.repository.OAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@Service
public class OAuthUserDetailsService implements UserDetailsService {

    private final OAuthUserRepository oAuthUserRepository;

    @Autowired
    public OAuthUserDetailsService(OAuthUserRepository oAuthUserRepository) {
        this.oAuthUserRepository = oAuthUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuthUser user = oAuthUserRepository.findOneByUsername(username);
        if (user == null) {
            return null;
        }
        return new User(user.getUsername(), user.getPassword(), Collections.singleton(Authority.ROLE_USER));
    }

    public enum Authority implements GrantedAuthority {
        ROLE_USER;

        public String getAuthority() {
            return name();
        }
    }
}
