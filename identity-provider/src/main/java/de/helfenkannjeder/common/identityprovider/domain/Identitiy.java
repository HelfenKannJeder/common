package de.helfenkannjeder.common.identityprovider.domain;

import javax.persistence.*;

@Entity
@Table(
		uniqueConstraints = @UniqueConstraint(columnNames = {"authProvider", "externalId"})
)
public class Identitiy extends AbstractVersionedAuditable {

	@Id
	@GeneratedValue
	private Long id = null;

	private String authProvider;
	private String externalId;

	@Column(unique = true)
	private String email;

	private String givenName;
	private String surname;
	private String phone;

	public Identitiy() {
	}

	public Identitiy(String email, String givenName, String surname, String phone) {
		this.email = email;
		this.givenName = givenName;
		this.surname = surname;
		this.phone = phone;
	}

	public void update(Identitiy identitiy) {
		this.email = identitiy.getEmail();
		this.givenName = identitiy.getGivenName();
		this.surname = identitiy.getSurname();
		this.phone = identitiy.getPhone();
	}

	public Long getId() {
		return id;
	}

	public Identitiy setId(Long id) {
		this.id = id;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public Identitiy setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getGivenName() {
		return givenName;
	}

	public Identitiy setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public Identitiy setSurname(String surname) {
		this.surname = surname;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Identitiy setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getAuthProvider() {
		return authProvider;
	}

	public Identitiy setAuthProvider(String authProvider) {
		this.authProvider = authProvider;
		return this;
	}

	public String getExternalId() {
		return externalId;
	}

	public Identitiy setExternalId(String externalId) {
		this.externalId = externalId;
		return this;
	}

	@Override
	public String toString() {
		return "Identitiy{" +
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
