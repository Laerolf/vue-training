package jp.co.company.space.api.features.locationCharacteristic.domain;

import java.util.*;

/**
 * An enum representing a characteristic of a planet.
 */
public enum PlanetCharacteristic {
    SILENT("silent"),
    CRATERED("cratered"),
    AIRLESS("airless"),
    METHANE_RICH("methane-rich"),
    RADIOACTIVE("radioactive"),
    OCEANIC("oceanic"),
    LIVING("living"),
    TOXIC("toxic"),
    CRUSHING("crushing"),
    DUSTY("dusty"),
    PLASMA("plasma"),
    ICY("icy"),
    MYSTERIOUS("mysterious"),
    ANCIENT("ancient"),
    HAZY("hazy"),
    DARK("dark"),
    BLUE("blue"),
    RED("red"),
    ORANGE("orange"),
    HOT("hot"),
    TEMPERATE("temperate"),
    COLD("cold");

    /**
     * The prefix used for the characteristic's locale code.
     */
    public final static String LOCALE_CODE_PREFIX = "planetCharacteristic";

    public final static Map<String, PlanetCharacteristic> BY_KEY = new HashMap<>();

    static {
        for (PlanetCharacteristic characteristic : values()) {
            BY_KEY.put(characteristic.getKey(), characteristic);
        }
    }

    /**
     * Gets a {@link PlanetCharacteristic} by its key, if any.
     * @param key The key to search for.
     * @return A {@link PlanetCharacteristic} or null if nothing was found.
     */
    public static PlanetCharacteristic getByKey(String key) {
        return BY_KEY.get(key);
    }

    /**
     * Gets a {@link List} of {@link PlanetCharacteristic}s for the provided keys.
     * @param characteristics The characteristic keys to search for.
     * @return A {@link List} of {@link PlanetCharacteristic}s.
     */
    public static List<PlanetCharacteristic> of(List<String> characteristics) {
        return characteristics.stream().map(PlanetCharacteristic::getByKey).filter(Objects::nonNull).toList();
    }

    /**
     * The key of the characteristic.
     */
    private final String key;

    /**
     * The locale code of the characteristic, used for translating.
     */
    private final String localeCode;

    PlanetCharacteristic(String key) {
        this.key = key;
        this.localeCode = String.join(".", LOCALE_CODE_PREFIX, this.key);
    }

    public String getKey() {
        return key;
    }

    public String getLocaleCode() {
        return localeCode;
    }
}
