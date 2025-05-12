package jp.co.company.space.api.features.catalog.domain;

/**
 * Represents the catalog topics.
 */
public enum CatalogTopic {
    GENDERS("genders"),
    MEAL_PREFERENCES("mealPreferences"),
    NATIONALITIES("nationalities"),
    PACKAGE_TYPES("packageTypes"),
    POD_TYPES("podTypes");

    /**
     * The label of the catalog topic.
     */
    private final String label;

    CatalogTopic(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
