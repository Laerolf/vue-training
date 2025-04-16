package jp.co.company.space.api.features.catalog.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.catalog.domain.MealPreference;
import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.catalog.dto.MealPreferenceDto;
import jp.co.company.space.api.features.catalog.dto.PackageTypeDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CatalogEndpoint} class.
 */
@HelidonTest
class CatalogEndpointTest {

    @Inject
    private WebTarget target;

    @Test
    void getAllMealPreferences() {
        // When
        Response response = target.path("catalog/meal-preferences").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<MealPreferenceDto> mealPreferences = response.readEntity(new GenericType<>() {
        });
        assertNotNull(mealPreferences);
        assertFalse(mealPreferences.isEmpty());

        Optional<MealPreferenceDto> chefsChoice = mealPreferences.stream().filter(mealPreferenceDto -> mealPreferenceDto.key.equals(MealPreference.CHEFS_CHOICE.getKey())).findFirst();

        assertTrue(chefsChoice.isPresent());
        assertEquals(MealPreference.CHEFS_CHOICE.getKey(), chefsChoice.get().key);
        assertEquals(MealPreference.CHEFS_CHOICE.getAdditionalCostPerDay(), chefsChoice.get().additionalCostPerDay);
        assertEquals(MealPreference.CHEFS_CHOICE.getAvailableFrom().getKey(), chefsChoice.get().availableFrom);
        assertEquals(MealPreference.CHEFS_CHOICE.getFreeFrom().getKey(), chefsChoice.get().freeFrom);
    }

    @Test
    void getAllPackageTypes() {
        // When
        Response response = target.path("catalog/package-types").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<PackageTypeDto> packageTypes = response.readEntity(new GenericType<>() {
        });
        assertNotNull(packageTypes);
        assertFalse(packageTypes.isEmpty());

        Optional<PackageTypeDto> economy = packageTypes.stream().filter(mealPreferenceDto -> mealPreferenceDto.key.equals(PackageType.ECONOMY.getKey())).findFirst();

        assertTrue(economy.isPresent());
        assertEquals(PackageType.ECONOMY.getKey(), economy.get().key);
    }
}