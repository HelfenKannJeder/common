package de.helfenkannjeder.common.identityprovider.rest.dto;

import de.helfenkannjeder.common.identityprovider.domain.AuthenticationProvider;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.rest.dto.validation.ValidAuthenticationProvider;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class IdentityDto {

	private Long id;

	@NotNull
	@ValidAuthenticationProvider()
	private String authProvider;

	private String externalId;

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

	public IdentityDto(Long id, String authProvider, String externalId, String email, String givenName, String surname, String phone) {
		this.id = id;
		this.authProvider = authProvider;
		this.externalId = externalId;
		this.email = email;
		this.givenName = givenName;
		this.surname = surname;
		this.phone = phone;
	}

	public static IdentityDto createFullDto(Identity identity) {
		return new IdentityDto(identity.getId(), identity.getAuthProvider().getApiName(), identity.getExternalId(), identity.getEmail(), identity.getGivenName(), identity.getSurname(), identity.getPhone());
	}

	public static Identity createIdentity(IdentityDto identity) {
		return new Identity(identity.getId(), AuthenticationProvider.getByApiName(identity.getAuthProvider()), identity.getExternalId(), identity.email, identity.givenName, identity.surname, identity.phone);
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

	public String getAuthProvider() {
		return authProvider;
	}

	public IdentityDto setAuthProvider(String authProvider) {
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
}
