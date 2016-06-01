package de.helfenkannjeder.common.identityprovider.rest.dto;

import de.helfenkannjeder.common.identityprovider.domain.Identitiy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class IdentityDto {

	private Long id;

	@NotEmpty(message = "not.empty")
	@Email(message = "not.invalid")
	private String email;

	@NotEmpty(message = "not.empty")
	private String givenName;

	@NotEmpty(message = "not.empty")
	private String surname;


	private String phone;


	public IdentityDto() {
	}

	public IdentityDto(String email, String givenName, String surname, String phone) {
		this.email = email;
		this.givenName = givenName;
		this.surname = surname;
		this.phone = phone;
	}

	public static IdentityDto createFullDto(Identitiy identitiy) {
		return new IdentityDto(identitiy.getEmail(), identitiy.getGivenName(), identitiy.getSurname(), identitiy.getPhone());
	}

	public static Identitiy createUser(IdentityDto user) {
		return new Identitiy(user.email, user.givenName, user.surname, user.phone);
	}

	public String getEmail() {
		return email;
	}

	public IdentityDto setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getGivenName() {
		return givenName;
	}

	public IdentityDto setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public IdentityDto setSurname(String surname) {
		this.surname = surname;
		return this;
	}


	public String getPhone() {
		return phone;
	}

	public IdentityDto setPhone(String phone) {
		this.phone = phone;
		return this;
	}
}
