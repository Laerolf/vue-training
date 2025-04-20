package jp.co.company.space.api.features.catalog.dto;

import jp.co.company.space.api.features.catalog.domain.MealPreference;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.examples.*;

/**
 * A POJO representing a DTO of a {@link MealPreference}.
 */
@Schema(name = "MealPreference", description = "The details of a meal preference.")
public class MealPreferenceDto {

    /**
     * Creates a {@link MealPreferenceDto} instance based on a {@link MealPreference} instance.
     *
     * @param mealPreference The base for the {@link MealPreferenceDto} instance.
     * @return A {@link MealPreferenceDto} instance.
     */
    public static MealPreferenceDto create(MealPreference mealPreference) {
        if (mealPreference == null) {
            throw new IllegalArgumentException("The base meal preference of the meal preference is missing.");
        }

        return new MealPreferenceDto(mealPreference.getKey(), mealPreference.getAdditionalCostPerDay(), mealPreference.getAvailableFrom().getKey(), mealPreference.getFreeFrom().getKey());
    }

    @Schema(description = "The key of the meal preference.", example = MEAL_PREFERENCE_KEY_EXAMPLE)
    public String key;

    @Schema(description = "The additional cost of the meal preference per day if the meal preference is not included in a package type.", example = MEAL_PREFERENCE_ADDITIONAL_COST_EXAMPLE)
    public double additionalCostPerDay;

    @Schema(description = "The minimum package type for the meal preference.", example = MEAL_PREFERENCE_AVAILABLE_FROM_EXAMPLE)
    public String availableFrom;

    @Schema(description = "The minimum package type where the meal preference is free of charge.", example = MEAL_PREFERENCE_FREE_FROM_EXAMPLE)
    public String freeFrom;

    protected MealPreferenceDto() {
    }

    protected MealPreferenceDto(String key, double additionalCostPerDay, String availableFrom, String freeFrom) {
        if (key == null) {
            throw new IllegalArgumentException("The key of the meal preference is missing.");
        } else if (availableFrom == null) {
            throw new IllegalArgumentException("The package type that makes the meal preference available is missing.");
        } else if (freeFrom == null) {
            throw new IllegalArgumentException("The package type that makes the meal preference free of charge is missing.");
        }

        this.key = key;
        this.additionalCostPerDay = additionalCostPerDay;
        this.availableFrom = availableFrom;
        this.freeFrom = freeFrom;
    }
}
