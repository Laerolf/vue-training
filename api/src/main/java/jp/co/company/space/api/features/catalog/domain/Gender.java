package jp.co.company.space.api.features.catalog.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Represents the accepted gender values.
 */
public enum Gender implements CatalogItem {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    private static final Map<String, Gender> BY_KEY = new HashMap<>();
    private static final String LABEL_PREFIX = "gender";

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

    private final String key;
    private final String label;

    Gender(String key) {
        this.key = key;
        this.label = new StringJoiner(".").add(LABEL_PREFIX).add(key).toString();
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
