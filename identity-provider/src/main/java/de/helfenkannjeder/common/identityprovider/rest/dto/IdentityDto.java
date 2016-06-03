package de.helfenkannjeder.common.identityprovider.rest.dto;

import de.helfenkannjeder.common.identityprovider.domain.Identity;
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

	@NotEmpty(message = "not.empty")
	private String authProvider;

	@NotEmpty(message = "not.empty")
	private String externalId;

	private String phone;


	public IdentityDto() {
	}

	public IdentityDto(String email, String givenName, String surname, String phone) {
		this.email = email;
		this.givenName = givenName;
		this.surname = surname;
		this.phone = phone;
	}

	public static IdentityDto createFullDto(Identity identity) {
		return new IdentityDto(identity.getEmail(), identity.getGivenName(), identity.getSurname(), identity.getPhone());
	}

	public static Identity createUser(IdentityDto user) {
		return new Identity(user.email, user.givenName, user.surname, user.phone);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(String authProvider) {
		this.authProvider = authProvider;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getPhone() {
		return phone;
	}

	public IdentityDto setPhone(String phone) {
		this.phone = phone;
		return this;
	}
}
