package de.helfenkannjeder.common.identityprovider.service;

import com.google.common.collect.Lists;
import de.helfenkannjeder.common.identityprovider.domain.Identity;
import de.helfenkannjeder.common.identityprovider.domain.repository.IdentityRepository;
import de.helfenkannjeder.common.identityprovider.service.exception.ConcurrentDeletedException;
import de.helfenkannjeder.common.identityprovider.service.exception.DuplicateResourceException;
import de.helfenkannjeder.common.identityprovider.service.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class IdentityService {

    private final IdentityRepository identityRepository;

    @Autowired
    public IdentityService(IdentityRepository identityRepository) {
        this.identityRepository = identityRepository;
    }

    public List<Identity> findAll() {
        return Lists.newArrayList(identityRepository.findAll());
    }

    public Identity findByEmail(String email) {
        return identityRepository.findByEmail(email);
    }

    public Identity findById(Long id) {
        return identityRepository.findOne(id);
    }

    public Identity createUser(Identity identity) {
        if (identity == null) {
            return identity;
        }

        Identity tmp = identityRepository.findByEmail(identity.getEmail());
        if (tmp != null) {
            throw new DuplicateResourceException(format("An identity with email %s already exists", identity.getEmail()));
        }

        return identityRepository.save(identity);
    }

    public Identity updateUser(Identity identity) {
        if (identity == null) {
            return null;
        }

        if (identity.getId() == null) {
            throw InvalidDataException.forSingleError("id.not.null", null);
        }

        Identity tmp = identityRepository.findOne(identity.getId());
        if (tmp == null) {
            throw new ConcurrentDeletedException(identity.getId());
        }

        return identityRepository.save(identity);
    }

    //TODO should we really delete users or to be able to track historical stuff just soft-delete/deactivate them?
    public void deleteUserById(Long id) {
        Identity identity = identityRepository.findOne(id);
        if (identity == null) {
            return;
        }

        identityRepository.delete(id);
    }

    public void deleteUser(Identity identity) {
        if (identity == null) {
            return;
        }

        deleteUserById(identity.getId());
    }
}
