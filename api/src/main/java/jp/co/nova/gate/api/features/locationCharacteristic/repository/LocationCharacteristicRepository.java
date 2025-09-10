package jp.co.nova.gate.api.features.locationCharacteristic.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jp.co.nova.gate.api.features.booking.exception.BookingException;
import jp.co.nova.gate.api.features.location.exception.LocationException;
import jp.co.nova.gate.api.features.locationCharacteristic.domain.LocationCharacteristic;
import jp.co.nova.gate.api.features.locationCharacteristic.exception.LocationCharacteristicError;
import jp.co.nova.gate.api.features.locationCharacteristic.exception.LocationCharacteristicException;

import java.util.List;
import java.util.Optional;

/**
 * The class for {@link LocationCharacteristic} DB actions.
 */
@ApplicationScoped
public class LocationCharacteristicRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected LocationCharacteristicRepository() {
    }

    /**
     * Returns an {@link Optional} persisted {@link LocationCharacteristic} matching the provided location characteristic location ID.
     *
     * @param id The location characteristic location ID to search with.
     * @return An {@link Optional} persisted {@link LocationCharacteristic}.
     */
    public Optional<LocationCharacteristic> findById(String id) throws LocationException {
        try {
            return entityManager.createNamedQuery("LocationCharacteristic.selectById", LocationCharacteristic.class).setParameter("id", id).getResultStream().findFirst();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException |
                 NullPointerException exception) {
            throw new LocationCharacteristicException(LocationCharacteristicError.FIND_BY_ID, exception);
        }
    }

    /**
     * Persists a {@link LocationCharacteristic}.
     *
     * @param locationCharacteristic The {@link LocationCharacteristic} to persist.
     * @return A persisted {@link LocationCharacteristic}.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public LocationCharacteristic save(LocationCharacteristic locationCharacteristic) throws LocationCharacteristicException {
        if (findById(locationCharacteristic.getId()).isEmpty()) {
            try {
                entityManager.persist(locationCharacteristic);
                return locationCharacteristic;
            } catch (TransactionRequiredException | EntityExistsException | IllegalArgumentException exception) {
                throw new LocationCharacteristicException(LocationCharacteristicError.SAVE, exception);
            }
        } else {
            return merge(locationCharacteristic);
        }
    }

    /**
     * Merges a {@link LocationCharacteristic} with a persisted {@link LocationCharacteristic}.
     *
     * @param locationCharacteristic The {@link LocationCharacteristic} to merge.
     * @return A persisted {@link LocationCharacteristic}.
     */
    public LocationCharacteristic merge(LocationCharacteristic locationCharacteristic) throws LocationCharacteristicException {
        try {
            return entityManager.merge(locationCharacteristic);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new LocationCharacteristicException(LocationCharacteristicError.MERGE, exception);
        }
    }

    /**
     * Saves a {@link List} of {@link LocationCharacteristic}s.
     *
     * @param locationCharacteristics The {@link List} of {@link LocationCharacteristic}s to save.
     * @return A {@link List} of persisted {@link LocationCharacteristic}s.
     */
    public List<LocationCharacteristic> save(List<LocationCharacteristic> locationCharacteristics) throws BookingException {
        try {
            return locationCharacteristics.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new LocationCharacteristicException(LocationCharacteristicError.SAVE_LIST, exception);
        }
    }

}
