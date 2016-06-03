package de.helfenkannjeder.common.identityprovider.domain.repository;

import de.helfenkannjeder.common.identityprovider.domain.Identitiy;
import org.springframework.data.repository.CrudRepository;

public interface IdentityRepository extends CrudRepository<Identitiy, Long> {

    Identitiy findByEmail(String email);

    Identitiy findByAuthProviderAndExternalId(String authProvider, String externalId);
}
