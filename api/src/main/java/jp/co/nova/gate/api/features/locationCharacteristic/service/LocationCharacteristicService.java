package jp.co.nova.gate.api.features.locationCharacteristic.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jp.co.nova.gate.api.features.locationCharacteristic.domain.LocationCharacteristic;
import jp.co.nova.gate.api.features.locationCharacteristic.domain.PlanetCharacteristic;
import jp.co.nova.gate.api.features.locationCharacteristic.exception.LocationCharacteristicError;
import jp.co.nova.gate.api.features.locationCharacteristic.exception.LocationCharacteristicException;
import jp.co.nova.gate.api.features.locationCharacteristic.repository.LocationCharacteristicRepository;
import jp.co.nova.gate.api.shared.util.LogBuilder;

import java.util.List;
import java.util.logging.Logger;

/**
 * A service class handling the {@link LocationCharacteristic} topic.
 */
@ApplicationScoped
public class LocationCharacteristicService {

    private static final Logger LOGGER = Logger.getLogger(LocationCharacteristicService.class.getName());

    @Inject
    private LocationCharacteristicRepository repository;

    private LocationCharacteristicService() {
    }

    /**
     * Returns a new {@link LocationCharacteristic} based on the provided characteristic and location.
     *
     * @param characteristic The characteristic to create a new location characteristic with.
     * @return A new {@link LocationCharacteristic}.
     */
    public LocationCharacteristic create(PlanetCharacteristic characteristic) {
        try {
            return LocationCharacteristic.create(characteristic);
        } catch (LocationCharacteristicException exception) {
            LOGGER.warning(new LogBuilder(LocationCharacteristicError.CREATE).withException(exception).build());
            throw exception;
        }
    }

    /**
     * Saves a {@link LocationCharacteristic}.
     *
     * @param locationCharacteristic The {@link LocationCharacteristic} to save.
     * @return A saved {@link LocationCharacteristic}.
     */
    public LocationCharacteristic save(LocationCharacteristic locationCharacteristic) throws LocationCharacteristicException {
        try {
            return repository.save(locationCharacteristic);
        } catch (LocationCharacteristicException exception) {
            LOGGER.warning(new LogBuilder(LocationCharacteristicError.SAVE).withException(exception).withProperty("id", locationCharacteristic.getId()).build());
            throw exception;
        }
    }

    /**
     * Saves a {@link List} of {@link LocationCharacteristic}s.
     *
     * @param locationCharacteristics The a {@link List} of {@link LocationCharacteristic}s to save.
     * @return A {@link List} of saved {@link LocationCharacteristic}s.
     */
    public List<LocationCharacteristic> save(List<LocationCharacteristic> locationCharacteristics) throws LocationCharacteristicException {
        try {
            return repository.save(locationCharacteristics);
        } catch (LocationCharacteristicException exception) {
            LOGGER.warning(new LogBuilder(LocationCharacteristicError.SAVE_LIST).withException(exception).build());
            throw exception;
        }
    }
}
