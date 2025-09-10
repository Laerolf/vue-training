package jp.co.nova.gate.api.features.passenger.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jakarta.transaction.Transactional;
import jp.co.nova.gate.api.features.passenger.domain.PersonalInformation;
import jp.co.nova.gate.api.features.passenger.exception.PersonalInformationError;
import jp.co.nova.gate.api.features.passenger.exception.PersonalInformationException;

import java.util.Optional;

/**
 * The class for {@link PersonalInformation} DB actions.
 */
@ApplicationScoped
public class PersonalInformationRepository {

    // TODO: add a way to remove existing personal information

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected PersonalInformationRepository() {
    }

    /**
     * Gets an {@link Optional} {@link PersonalInformation} matching an ID.
     *
     * @param id The ID to search with.
     * @return An {@link Optional} {@link PersonalInformation}.
     */
    public Optional<PersonalInformation> findById(String id) throws PersonalInformationException {
        try {
            return Optional.ofNullable(entityManager.find(PersonalInformation.class, id));
        } catch (IllegalArgumentException exception) {
            throw new PersonalInformationException(PersonalInformationError.FIND_BY_ID, exception);
        }
    }

    /**
     * Saves a {@link PersonalInformation}.
     *
     * @param personalInformation The {@link PersonalInformation} to save.
     * @return The saved {@link PersonalInformation}.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public PersonalInformation save(PersonalInformation personalInformation) throws PersonalInformationException {
        if (findById(personalInformation.getId()).isEmpty()) {
            try {
                entityManager.persist(personalInformation);
                return personalInformation;
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new PersonalInformationException(PersonalInformationError.SAVE, exception);
            }
        } else {
            return merge(personalInformation);
        }
    }

    /**
     * Merges a persisted {@link PersonalInformation}.
     *
     * @param personalInformation The {@link PersonalInformation} to merge.
     * @return The merged {@link PersonalInformation}.
     */
    public PersonalInformation merge(PersonalInformation personalInformation) throws PersonalInformationException {
        try {
            return entityManager.merge(personalInformation);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new PersonalInformationException(PersonalInformationError.MERGE, exception);
        }
    }
}
