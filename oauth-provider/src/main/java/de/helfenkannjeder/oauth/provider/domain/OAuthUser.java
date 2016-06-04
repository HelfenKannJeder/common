package de.helfenkannjeder.oauth.provider.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
@Entity
public class OAuthUser {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    public OAuthUser() {
    }

    public OAuthUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}