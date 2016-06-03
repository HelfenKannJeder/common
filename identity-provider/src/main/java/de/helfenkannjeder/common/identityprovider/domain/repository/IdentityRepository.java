package de.helfenkannjeder.common.identityprovider.domain.repository;

import de.helfenkannjeder.common.identityprovider.domain.Identity;
import org.springframework.data.repository.CrudRepository;

public interface IdentityRepository extends CrudRepository<Identity, Long> {

    Identity findByEmail(String email);

    Identity findByAuthProviderAndExternalId(String authProvider, String externalId);
}
