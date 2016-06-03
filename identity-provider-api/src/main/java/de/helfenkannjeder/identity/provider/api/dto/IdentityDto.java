package de.helfenkannjeder.identity.provider.api.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class IdentityDto {

	private Long id;

	@NotNull
	private AuthenticationProvider authProvider;

	private String externalId;

	@NotEmpty(message = "not.empty")
	@Email(message = "not.invalid")
	private String email;

	@NotEmpty(message = "not.empty")
	private String givenName;

	@NotEmpty(message = "not.empty")
	private String surname;

	private String phone;

	private String status;

	private String confirmationCode;

	public IdentityDto() {
	}

	public IdentityDto(Long id, AuthenticationProvider authProvider, String externalId, String email, String givenName, String surname, String phone, String status, String confirmationCode) {
		this.id = id;
		this.authProvider = authProvider;
		this.externalId = externalId;
		this.email = email;
		this.givenName = givenName;
		this.surname = surname;
		this.phone = phone;
		this.status = status;
		this.confirmationCode = confirmationCode;
	}


	public Long getId() {
		return id;
	}

	public IdentityDto setId(Long id) {
		this.id = id;
		return this;
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

	public AuthenticationProvider getAuthProvider() {
		return authProvider;
	}

	public IdentityDto setAuthProvider(AuthenticationProvider authProvider) {
		this.authProvider = authProvider;
		return this;
	}

	public String getExternalId() {
		return externalId;
	}

	public IdentityDto setExternalId(String externalId) {
		this.externalId = externalId;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public IdentityDto setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}
}
