package de.helfenkannjeder.common.identityprovider.domain;

import javax.persistence.*;

@Entity
@Table(
		uniqueConstraints = @UniqueConstraint(columnNames = {"authProvider", "externalId"})
)
public class User extends AbstractVersionedAuditable {

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

	public User() {
	}

	public User(String email, String givenName, String surname, String phone) {
		this.email = email;
		this.givenName = givenName;
		this.surname = surname;
		this.phone = phone;
	}

	public void update(User user) {
		this.email = user.getEmail();
		this.givenName = user.getGivenName();
		this.surname = user.getSurname();
		this.phone = user.getPhone();
	}

	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getGivenName() {
		return givenName;
	}

	public User setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}

	public String getSurname() {
		return surname;
	}

	public User setSurname(String surname) {
		this.surname = surname;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getAuthProvider() {
		return authProvider;
	}

	public User setAuthProvider(String authProvider) {
		this.authProvider = authProvider;
		return this;
	}

	public String getExternalId() {
		return externalId;
	}

	public User setExternalId(String externalId) {
		this.externalId = externalId;
		return this;
	}

	@Override
	public String toString() {
		return "User{" +
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
