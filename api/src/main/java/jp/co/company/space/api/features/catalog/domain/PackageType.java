package jp.co.company.space.api.features.catalog.domain;

import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * An enum representing a package type for a passenger's booking.
 */
public enum PackageType implements CatalogItem {
    ECONOMY("economy"),
    BUSINESS("business"),
    FIRST_CLASS("firstClass");

    private static final String CATALOG_PREFIX = "packageType";

    /**
     * Searches a {@link PackageType} by its key.
     *
     * @param key The key search for a package type.
     * @return An {@link Optional} {@link PackageType}.
     */
    public static Optional<PackageType> findByKey(String key) {
        return Arrays.stream(PackageType.values()).filter(packageType -> packageType.key.equals(key)).findFirst();
    }

    /**
     * The key of the package type.
     */
    private final String key;

    /**
     * The label of the package type.
     */
    private final String label;

    PackageType(String key) {
        this.key = key;
        this.label = new StringJoiner(".").add(CATALOG_PREFIX).add(key).toString();
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
