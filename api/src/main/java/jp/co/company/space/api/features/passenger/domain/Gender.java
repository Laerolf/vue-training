package jp.co.company.space.api.features.passenger.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO: Expose in the catalog

/**
 * The gender of a passenger.
 */
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private final String key;

    private static final Map<String, Gender> BY_KEY = new HashMap<>();

    static {
        for (Gender gender : values()) {
            BY_KEY.put(gender.key, gender);
        }
    }

    /**
     * Gets an {@link Optional} {@link Gender} value for the provided key.
     *
     * @param key The key to search for.
     * @return An {@link Optional} {@link Gender}.
     */
    public static Optional<Gender> findByKey(String key) {
        return Optional.ofNullable(BY_KEY.get(key));
    }

    Gender(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
