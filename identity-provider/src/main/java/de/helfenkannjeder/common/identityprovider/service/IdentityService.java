package de.helfenkannjeder.common.identityprovider.service;

import com.google.common.collect.Lists;
import de.helfenkannjeder.common.identityprovider.domain.Identitiy;
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

    public List<Identitiy> findAll() {
        return Lists.newArrayList(identityRepository.findAll());
    }

    public Identitiy findByEmail(String email) {
        return identityRepository.findByEmail(email);
    }

    public Identitiy findById(Long id) {
        return identityRepository.findOne(id);
    }

    public Identitiy createUser(Identitiy identitiy) {
        if (identitiy == null) {
            return identitiy;
        }

        Identitiy tmp = identityRepository.findByEmail(identitiy.getEmail());
        if (tmp != null) {
            throw new DuplicateResourceException(format("An identitiy with email %s already exists", identitiy.getEmail()));
        }

        return identityRepository.save(identitiy);
    }

    public Identitiy updateUser(Identitiy identitiy) {
        if (identitiy == null) {
            return null;
        }

        if (identitiy.getId() == null) {
            throw InvalidDataException.forSingleError("id.not.null", null);
        }

        Identitiy tmp = identityRepository.findOne(identitiy.getId());
        if (tmp == null) {
            throw new ConcurrentDeletedException(identitiy.getId());
        }

        return identityRepository.save(identitiy);
    }

    //TODO should we really delete users or to be able to track historical stuff just soft-delete/deactivate them?
    public void deleteUserById(Long id) {
        Identitiy identitiy = identityRepository.findOne(id);
        if (identitiy == null) {
            return;
        }

        identityRepository.delete(id);
    }

    public void deleteUser(Identitiy identitiy) {
        if (identitiy == null) {
            return;
        }

        deleteUserById(identitiy.getId());
    }
}
