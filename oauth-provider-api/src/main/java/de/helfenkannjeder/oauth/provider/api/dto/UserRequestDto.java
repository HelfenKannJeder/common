package de.helfenkannjeder.oauth.provider.api.dto;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class UserRequestDto {

    private String username;
    private String password;

    public UserRequestDto() {
        // Setup after creation
    }

    public UserRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
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
