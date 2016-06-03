package de.helfenkannjeder.common.identityprovider.service;

import com.google.common.collect.Lists;
import de.helfenkannjeder.common.identityprovider.domain.DomainAuthenticationProvider;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.domain.IdentityStatus;
import de.helfenkannjeder.common.identityprovider.domain.repository.IdentityRepository;
import de.helfenkannjeder.common.identityprovider.service.exception.DuplicateResourceException;
import de.helfenkannjeder.common.identityprovider.service.exception.InvalidDataException;
import de.helfenkannjeder.common.identityprovider.service.exception.ResourceNotFoundException;
import de.helfenkannjeder.oauth.provider.api.OAuthProviderUserManagementApi;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Service
public class IdentityService {

	private final IdentityRepository identityRepository;
	private final OAuthProviderUserManagementApi helfenKannJederOAuthProviderApi;

	@Autowired
	public IdentityService(IdentityRepository identityRepository, OAuthProviderUserManagementApi helfenKannJederOAuthProviderApi) {
		this.identityRepository = identityRepository;
		this.helfenKannJederOAuthProviderApi = helfenKannJederOAuthProviderApi;
	}

	public List<Identity> findAll() {
		return Lists.newArrayList(identityRepository.findAll());
	}

	public Identity findByEmail(String email) {
		return identityRepository.findByEmail(email);
	}

	public Identity findById(Long id) {
		Identity identity = identityRepository.findOne(id);

		if (identity == null) {
			throw new ResourceNotFoundException(id);
		}

		return identity;
	}

	public Identity createIdentity(Identity identity) {
		if (identity == null) {
			return null;
		}

		Identity alreadyInDb = identityRepository.findById(identity.getId());
		if (alreadyInDb != null) {
			throw new DuplicateResourceException(format("An identity with id %s already exists", identity.getId()));
		}
		alreadyInDb = identityRepository.findByAuthProviderAndExternalId(identity.getAuthProvider(), identity.getExternalId());
		if (alreadyInDb != null) {
			throw new DuplicateResourceException(format("An identity with authenticationProvider %s and externalId %s already exists", identity.getAuthProvider(), identity.getExternalId()));
		}

		if (identity.getAuthProvider() == DomainAuthenticationProvider.HELFENKANNJEDER) {
			identity.setStatus(IdentityStatus.INACTIVE);
			identity.setConfirmationCode(generateConfirmationCode());
		}

		return identityRepository.save(identity);
	}

//	private String createHelfenKannJederOAuthUser(String email) {
//		PasswordGenerator passwordGenerator = new PasswordGenerator();
//		List<CharacterRule> passwordRules = Arrays.asList(
//				new CharacterRule(EnglishCharacterData.UpperCase, 1),
//				new CharacterRule(EnglishCharacterData.LowerCase, 1),
//				new CharacterRule(EnglishCharacterData.Digit, 1));
//		String password = passwordGenerator.generatePassword(8, passwordRules);
//
//		UserResponseDto createdUser = helfenKannJederOAuthProviderApi.create(new UserRequestDto(email, password));
//
//		return createdUser.getId();
//	}

	public Identity updateIdentity(Identity identity) {
		if (identity == null) {
			return null;
		}

		if (identity.getId() == null) {
			throw InvalidDataException.forSingleError("id.not.null", null);
		}

		Identity storedEntity = identityRepository.findOne(identity.getId());
		if (storedEntity == null) {
			throw new ResourceNotFoundException(identity.getId());
		}
		storedEntity.update(identity);

		return identityRepository.save(storedEntity);
	}

	public void deleteIdentityById(Long id) {
		Identity identity = identityRepository.findOne(id);
		if (identity == null) {
			return;
		}

		identityRepository.delete(id);
	}

	public void deleteIdentity(Identity identity) {
		if (identity == null) {
			return;
		}

		deleteIdentityById(identity.getId());
	}

	private String generateConfirmationCode() {
		PasswordGenerator validationCodeGenerator = new PasswordGenerator();
		List<CharacterRule> validationCodeRules = Arrays.asList(
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				new CharacterRule(EnglishCharacterData.Digit, 1));
		return validationCodeGenerator.generatePassword(16, validationCodeRules);
	}
}
