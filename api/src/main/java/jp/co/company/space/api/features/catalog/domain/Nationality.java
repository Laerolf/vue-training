package jp.co.company.space.api.features.catalog.domain;

import jp.co.company.space.api.features.passenger.exception.PersonalInformationError;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationException;
import jp.co.company.space.api.shared.util.LogBuilder;

import java.util.*;
import java.util.logging.Logger;

// TODO: Expose in the catalog

/**
 * Represents the accepted nationality values.
 */
public class Nationality implements CatalogItem {

    private static final Logger LOGGER = Logger.getLogger(Nationality.class.getName());

    /**
     * All possible {@link Nationality} values mapped by their locale ISO code.
     */
    private static final Map<String, CatalogItem> BY_KEY = new LinkedHashMap<>();

    private final String key;
    private final String label;

    static {
        for (String isoCode : Locale.getISOCountries()) {
            Nationality nationality = Nationality.create(isoCode);
            BY_KEY.put(nationality.getKey(), nationality);
        }
    }

    /**
     * Gets all possible {@link Nationality} values.
     *
     * @return A {@link List} of {@link Nationality}.
     */
    public static List<CatalogItem> getAllNationalities() {
        return BY_KEY.values().stream().toList();
    }

    /**
     * Creates a new {@link Nationality} based on the provided ISO code.
     *
     * @param isoCode The ISO code of the nationality.
     * @return A {@link Nationality} values.
     */
    public static Nationality create(String isoCode) throws PersonalInformationException {
        return new Nationality(isoCode);
    }

    /**
     * Tests whether the provided ISO code is valid.
     *
     * @param isoCode The ISO code to test.
     * @return True when the ISO code is valid, false otherwise.
     */
    public static boolean isValid(String isoCode) {
        return List.of(Locale.getISOCountries()).contains(isoCode);
    }

    /**
     * Returns a {@link Nationality} matching the provided ISO code if any match.
     *
     * @param isoCode The ISO code to search with.
     * @return A {@link Nationality}.
     */
    public static Optional<Nationality> findNationalityByIsoCode(String isoCode) {
        return Optional.ofNullable((Nationality) BY_KEY.get(isoCode));
    }

    protected Nationality(String isoCode) throws PersonalInformationException {
        try {
            if (isoCode == null) {
                throw new IllegalArgumentException("The ISO code is missing.");
            } else if (!isValid(isoCode)) {
                throw new IllegalArgumentException("The provided ISO code is invalid.");
            }

            Locale locale = new Locale.Builder().setRegion(isoCode).build();

            this.key = isoCode;
            this.label = locale.getDisplayCountry();
        } catch (IllegalArgumentException exception) {
            LOGGER.warning(new LogBuilder(PersonalInformationError.INVALID_NATIONALITY).withException(exception).withProperty("isoCode", isoCode).build());
            throw new PersonalInformationException(PersonalInformationError.INVALID_NATIONALITY, exception);
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Nationality that = (Nationality) o;
        return Objects.equals(key, that.key) && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, label);
    }
}
