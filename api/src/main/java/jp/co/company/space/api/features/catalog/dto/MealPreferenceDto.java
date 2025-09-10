package jp.co.company.space.api.features.catalog.dto;

import jp.co.company.space.api.features.catalog.domain.CatalogItem;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.exception.CatalogError;
import jp.co.company.space.api.features.catalog.exception.CatalogException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.*;

/**
 * A POJO representing a DTO of a {@link MealPreference}.
 */
@Schema(name = "MealPreference", description = "The details of a meal preference.")
public class MealPreferenceDto extends CatalogItemDto {

    /**
     * Creates a {@link MealPreferenceDto} based on a {@link MealPreference}.
     *
     * @param mealPreference The base for the {@link MealPreferenceDto}.
     * @return A {@link MealPreferenceDto}.
     */
    public static MealPreferenceDto create(MealPreference mealPreference) throws CatalogException {
        if (mealPreference == null) {
            throw new CatalogException(CatalogError.MEAL_PREFERENCE_MISSING);
        }

        return new MealPreferenceDto(mealPreference.getKey(), mealPreference.getLabel(), mealPreference.getAdditionalCostPerDay(), mealPreference.getAvailableFrom().getKey(), mealPreference.getFreeFrom().getKey());
    }

    /**
     * Creates a {@link MealPreferenceDto} based on a {@link CatalogItem}.
     *
     * @param catalogItem The base for the {@link MealPreferenceDto}.
     * @return A {@link MealPreferenceDto}.
     */
    public static MealPreferenceDto fromCatalogItem(CatalogItem catalogItem) throws CatalogException {
        if (!(catalogItem instanceof MealPreference)) {
            throw new CatalogException(CatalogError.CATALOG_ITEM_MEAL_PREFERENCE_MISMATCH);
        }

        return create((MealPreference) catalogItem);
    }

    @Schema(description = "The key of the meal preference.", example = MEAL_PREFERENCE_KEY_EXAMPLE)
    public String key;

    @Schema(description = "The label of the meal preference.", example = MEAL_PREFERENCE_LABEL_EXAMPLE)
    public String label;

    @Schema(description = "The additional cost of the meal preference per day if the meal preference is not included in a package type.", example = MEAL_PREFERENCE_ADDITIONAL_COST_EXAMPLE)
    public double additionalCostPerDay;

    @Schema(description = "The minimum package type for the meal preference.", example = MEAL_PREFERENCE_AVAILABLE_FROM_EXAMPLE)
    public String availableFrom;

    @Schema(description = "The minimum package type where the meal preference is free of charge.", example = MEAL_PREFERENCE_FREE_FROM_EXAMPLE)
    public String freeFrom;

    protected MealPreferenceDto() {
    }

    protected MealPreferenceDto(String key, String label, double additionalCostPerDay, String availableFrom, String freeFrom) throws CatalogException {
        if (key == null) {
            throw new CatalogException(CatalogError.MISSING_KEY);
        } else if (label == null) {
            throw new CatalogException(CatalogError.MISSING_LABEL);
        } else if (availableFrom == null) {
            throw new CatalogException(CatalogError.MEAL_PREFERENCE_MISSING_AVAILABLE_FROM);
        } else if (freeFrom == null) {
            throw new CatalogException(CatalogError.MEAL_PREFERENCE_MISSING_FREE_FROM);
        }

        this.key = key;
        this.label = label;
        this.additionalCostPerDay = additionalCostPerDay;
        this.availableFrom = availableFrom;
        this.freeFrom = freeFrom;
    }
}
