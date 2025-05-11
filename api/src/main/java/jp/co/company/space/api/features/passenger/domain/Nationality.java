package jp.co.company.space.api.features.passenger.domain;

import jp.co.company.space.api.features.passenger.exception.PersonalInformationError;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationException;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.util.*;
import java.util.logging.Logger;

// TODO: Expose in the catalog

/**
 * A POJO representing the nationality of a passenger.
 */
public class Nationality {

    private static final Logger LOGGER = Logger.getLogger(Nationality.class.getName());

    /**
     * All possible country names based on their locale ISO code.
     */
    private static final Map<String, String> BY_ISO_CODE = new LinkedHashMap<>();

    private final String isoCode;

    static {
        for (String isoCode : Locale.getISOCountries()) {
            Locale locale = new Locale.Builder().setRegion(isoCode).build();
            BY_ISO_CODE.put(isoCode, locale.getDisplayCountry());
        }
    }

    /**
     * Creates a new {@link Nationality} based on the provided ISO code.
     *
     * @param isoCode The ISO code of the nationality.
     * @return A {@link Nationality}.
     */
    public static Nationality create(String isoCode) throws PersonalInformationException {
        return new Nationality(isoCode);
    }

    /**
     * Gets a {@link Map} of all possible country names: the key is the ISO code of the country and the value is the name of the country.
     *
     * @return A {@link Map}.
     */
    public static Map<String, String> getAllCountryNames() {
        return BY_ISO_CODE;
    }

    /**
     * Tests whether the provided ISO code is valid.
     *
     * @param isoCode The ISO code to test.
     * @return True when the ISO code is valid, false otherwise.
     */
    public static boolean isValid(String isoCode) {
        return BY_ISO_CODE.containsKey(isoCode);
    }

    /**
     * Returns the name of the country matching the provided ISO code if any match.
     *
     * @param isoCode The ISO code to search for.
     * @return A country name.
     */
    public static Optional<String> findCountryNameByIsoCode(String isoCode) {
        return Optional.ofNullable(getAllCountryNames().get(isoCode));
    }

    protected Nationality(String isoCode) throws PersonalInformationException {
        try {
            if (isoCode == null) {
                throw new IllegalArgumentException("The ISO code is missing.");
            } else if (!isValid(isoCode)) {
                throw new IllegalArgumentException("The provided ISO code is invalid.");
            }

            this.isoCode = isoCode;
        } catch (IllegalArgumentException exception) {
            LOGGER.warning(new LogBuilder(PersonalInformationError.INVALID_NATIONALITY).withException(exception).withProperty("isoCode", isoCode).build());
            throw new PersonalInformationException(PersonalInformationError.INVALID_NATIONALITY, exception);
        }
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getCountryName() {
        return findCountryNameByIsoCode(isoCode).orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Nationality that = (Nationality) o;
        return Objects.equals(isoCode, that.isoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isoCode);
    }
}
