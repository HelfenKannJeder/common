package de.helfenkannjeder.common.identityprovider.domain;

import javax.persistence.*;

@Entity
@Table(
		uniqueConstraints = @UniqueConstraint(columnNames = {"authProvider", "externalId"})
)
public class Identity extends AbstractVersionedAuditable {

	@Id
	@GeneratedValue
	private Long id = null;

	private AuthenticationProvider authProvider;
	private String externalId;

	@Column(unique = true)
	private String email;

	private String givenName;
	private String surname;
	private String phone;

	public Identity() {
	}

	public Identity(Long id, AuthenticationProvider authProvider, String externalId, String email, String givenName, String surname, String phone) {
		this.id = id;
		this.authProvider = authProvider;
		this.externalId = externalId;
		this.email = email;
		this.givenName = givenName;
		this.surname = surname;
		this.phone = phone;
	}

	public void update(Identity identity) {
		this.email = identity.getEmail();
		this.givenName = identity.getGivenName();
		this.surname = identity.getSurname();
		this.phone = identity.getPhone();
	}

	public Long getId() {
		return id;
	}

	public Identity setId(Long id) {
		this.id = id;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Identity setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getGivenName() {
		return givenName;
	}

	public Identity setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public Identity setSurname(String surname) {
		this.surname = surname;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Identity setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public AuthenticationProvider getAuthProvider() {
		return authProvider;
	}

	public Identity setAuthProvider(AuthenticationProvider authProvider) {
		this.authProvider = authProvider;
		return this;
	}

	public String getExternalId() {
		return externalId;
	}

	public Identity setExternalId(String externalId) {
		this.externalId = externalId;
		return this;
	}

	@Override
	public String toString() {
		return "Identity{" +
				"id=" + id +
				", authProvider='" + authProvider + '\'' +
				", externalId='" + externalId + '\'' +
				", email='" + email + '\'' +
				", givenName='" + givenName + '\'' +
				", surname='" + surname + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}
