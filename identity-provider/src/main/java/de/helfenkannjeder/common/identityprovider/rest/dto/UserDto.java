package de.helfenkannjeder.common.identityprovider.rest.dto;

import de.helfenkannjeder.common.identityprovider.domain.Identitiy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDto {

	@NotEmpty(message = "not.empty")
	@Email(message = "not.invalid")
	private String email;

	@NotEmpty(message = "not.empty")
	private String givenName;

	@NotEmpty(message = "not.empty")
	private String surname;


	private String phone;


	public UserDto() {
	}

	public UserDto(String email, String givenName, String surname, String phone) {
		this.email = email;
		this.givenName = givenName;
		this.surname = surname;
		this.phone = phone;
	}

	public static UserDto createFullDto(Identitiy identitiy) {
		return new UserDto(identitiy.getEmail(), identitiy.getGivenName(), identitiy.getSurname(), identitiy.getPhone());
	}

	public static Identitiy createUser(UserDto user) {
		return new Identitiy(user.email, user.givenName, user.surname, user.phone);
	}

	public String getEmail() {
		return email;
	}

	public UserDto setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getGivenName() {
		return givenName;
	}

	public UserDto setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public UserDto setSurname(String surname) {
		this.surname = surname;
		return this;
	}


	public String getPhone() {
		return phone;
	}

	public UserDto setPhone(String phone) {
		this.phone = phone;
		return this;
	}
}
