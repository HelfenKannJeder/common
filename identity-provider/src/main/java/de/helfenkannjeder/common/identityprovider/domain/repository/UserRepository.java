package de.helfenkannjeder.common.identityprovider.domain.repository;

import de.helfenkannjeder.common.identityprovider.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByAuthProviderAndExternalId(String authProvider, String externalId);
}
