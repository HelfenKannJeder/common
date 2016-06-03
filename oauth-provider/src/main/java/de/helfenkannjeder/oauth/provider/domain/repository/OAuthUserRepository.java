package de.helfenkannjeder.oauth.provider.domain.repository;

import de.helfenkannjeder.oauth.provider.domain.OAuthUser;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Valentin Zickner <valentin.zickner@helfenkannjeder.de>
 */
public interface OAuthUserRepository extends CrudRepository<OAuthUser, String> {
}
