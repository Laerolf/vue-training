package jp.co.nova.gate.api.features.locationCharacteristic.dto;

import jp.co.nova.gate.api.features.location.exception.LocationException;
import jp.co.nova.gate.api.features.locationCharacteristic.domain.LocationCharacteristic;
import jp.co.nova.gate.api.features.locationCharacteristic.exception.LocationCharacteristicError;
import jp.co.nova.gate.api.features.locationCharacteristic.exception.LocationCharacteristicException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.nova.gate.api.shared.openApi.Examples.*;

/**
 * A POJO representing a brief DTO of a {@link LocationCharacteristic}.
 */
@Schema(name = "LocationCharacteristic", description = "The details of a location characteristic.")
public class LocationCharacteristicDto {

    /**
     * Creates a new {@link LocationCharacteristicDto} based on a {@link LocationCharacteristic}.
     *
     * @param locationCharacteristic The base {@link LocationCharacteristic}.
     * @return A new {@link LocationCharacteristicDto}.
     */
    public static LocationCharacteristicDto create(LocationCharacteristic locationCharacteristic) throws LocationException {
        return new LocationCharacteristicDto(locationCharacteristic.getId(), locationCharacteristic.getCharacteristic().getLabel(), locationCharacteristic.getCharacteristic().getLabel());
    }

    /**
     * The ID of the location characteristic.
     */
    @Schema(description = "The ID of the location characteristic.", example = ID_EXAMPLE)
    public String id;

    /**
     * The key of the location characteristic.
     */
    @Schema(description = "The key of the location characteristic.", example = LOCATION_CHARACTERISTICS_KEY_EXAMPLE)
    public String key;

    /**
     * The locale code of the location characteristic.
     */
    @Schema(description = "The locale code of the location characteristic.", example = LOCATION_CHARACTERISTICS_LABEL_EXAMPLE)
    public String localeCode;

    protected LocationCharacteristicDto() {
    }

    protected LocationCharacteristicDto(String id, String key, String localeCode) throws LocationCharacteristicException {
        if (id == null) {
            throw new LocationCharacteristicException(LocationCharacteristicError.MISSING_ID);
        } else if (key == null) {
            throw new LocationCharacteristicException(LocationCharacteristicError.MISSING_KEY);
        } else if (localeCode == null) {
            throw new LocationCharacteristicException(LocationCharacteristicError.MISSING_LOCALE_CODE);
        }

        this.id = id;
        this.key = key;
        this.localeCode = localeCode;
    }
}
