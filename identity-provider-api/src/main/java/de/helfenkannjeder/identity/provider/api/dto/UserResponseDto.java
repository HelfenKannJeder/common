package de.helfenkannjeder.identity.provider.api.dto;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public class UserResponseDto {

	private String id;

	public UserResponseDto() {
	}

	public UserResponseDto(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
