package jp.co.company.space.api.features.catalog.domain;

import java.util.Arrays;
import java.util.Optional;

/**
 * An enum representing a package type for a passenger's booking.
 */
public enum PackageType {
    ECONOMY("economy"),
    BUSINESS("business"),
    FIRST_CLASS("firstClass");

    /**
     * Searches a {@link PackageType} instance by its key.
     * @param key The key search for a package type.
     * @return An {@link Optional} {@link PackageType} instance.
     */
    public static Optional<PackageType> findByKey(String key) {
        return Arrays.stream(PackageType.values()).filter(packageType -> packageType.key.equals(key)).findFirst();
    }

    /**
     * The key of the package type.
     */
    private final String key;

    PackageType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
