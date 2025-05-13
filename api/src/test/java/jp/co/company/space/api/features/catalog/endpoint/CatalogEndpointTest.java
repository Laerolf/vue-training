package jp.co.company.space.api.features.catalog.endpoint;

import io.helidon.http.Status;
import io.helidon.microprofile.testing.junit5.HelidonTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import jp.co.company.space.api.features.catalog.domain.*;
import jp.co.company.space.api.features.catalog.dto.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CatalogEndpoint} class.
 */
@HelidonTest
class CatalogEndpointTest {

    @Inject
    private WebTarget target;

    @Test
    void getAllCatalogItems() {
        // When
        Response response = target.path("catalog").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        Map<String, List<CatalogItemDto>> allCatalogItems = response.readEntity(new GenericType<>() {
        });
        assertNotNull(allCatalogItems);
        assertEquals(CatalogTopic.values().length, allCatalogItems.size());

        assertTrue(Arrays.stream(CatalogTopic.values()).allMatch(catalogTopic -> allCatalogItems.containsKey(catalogTopic.getLabel())));
        assertTrue(allCatalogItems.values().stream().noneMatch(List::isEmpty));
    }

    @Test
    void getAllGenders() {
        // Given
        Gender expectedGender = Gender.MALE;

        // When
        Response response = target.path("catalog/genders").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<GenderDto> genders = response.readEntity(new GenericType<>() {
        });
        assertNotNull(genders);
        assertEquals(Gender.values().length, genders.size());

        GenderDto selectedGender = genders.stream()
                .filter(genderDto -> genderDto.key.equals(expectedGender.getKey()))
                .findFirst()
                .orElseThrow();

        assertEquals(expectedGender.getKey(), selectedGender.key);
        assertEquals(expectedGender.getLabel(), selectedGender.label);
    }

    @Test
    void getAllMealPreferences() {
        // Given
        MealPreference expectedMealPreference = MealPreference.CHEFS_CHOICE;

        // When
        Response response = target.path("catalog/meal-preferences").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<MealPreferenceDto> mealPreferences = response.readEntity(new GenericType<>() {
        });
        assertNotNull(mealPreferences);
        assertEquals(MealPreference.values().length, mealPreferences.size());

        MealPreferenceDto selectedMealPreference = mealPreferences.stream()
                .filter(mealPreferenceDto -> mealPreferenceDto.key.equals(expectedMealPreference.getKey()))
                .findFirst()
                .orElseThrow();

        assertEquals(expectedMealPreference.getKey(), selectedMealPreference.key);
        assertEquals(expectedMealPreference.getLabel(), selectedMealPreference.label);
        assertEquals(expectedMealPreference.getAdditionalCostPerDay(), selectedMealPreference.additionalCostPerDay);
        assertEquals(expectedMealPreference.getAvailableFrom().getKey(), selectedMealPreference.availableFrom);
        assertEquals(expectedMealPreference.getFreeFrom().getKey(), selectedMealPreference.freeFrom);
    }

    @Test
    void getAllNationalities() {
        // Given
        Nationality expectedNationality = Nationality.findNationalityByIsoCode("BE").orElseThrow();

        // When
        Response response = target.path("catalog/nationalities").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<NationalityDto> nationalities = response.readEntity(new GenericType<>() {
        });
        assertNotNull(nationalities);
        assertEquals(Locale.getISOCountries().length, nationalities.size());

        NationalityDto selectedNationality = nationalities.stream()
                .filter(nationalityDto -> nationalityDto.key.equals(expectedNationality.getKey()))
                .findFirst()
                .orElseThrow();

        assertEquals(expectedNationality.getKey(), selectedNationality.key);
        assertEquals(expectedNationality.getLabel(), selectedNationality.label);
    }

    @Test
    void getAllPackageTypes() {
        // Given
        PackageType expectedPackageType = PackageType.ECONOMY;

        // When
        Response response = target.path("catalog/package-types").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<PackageTypeDto> packageTypes = response.readEntity(new GenericType<>() {
        });
        assertNotNull(packageTypes);
        assertEquals(PackageType.values().length, packageTypes.size());

        PackageTypeDto selectedPackageType = packageTypes.stream()
                .filter(mealPreferenceDto -> mealPreferenceDto.key.equals(PackageType.ECONOMY.getKey()))
                .findFirst()
                .orElseThrow();

        assertEquals(expectedPackageType.getKey(), selectedPackageType.key);
        assertEquals(expectedPackageType.getLabel(), selectedPackageType.label);
    }

    @Test
    void getAllPodTypes() {
        // Given
        PodType expectedPodType = PodType.STANDARD_POD;

        // When
        Response response = target.path("catalog/pod-types").request().get();

        // Then
        assertNotNull(response);
        assertEquals(Status.OK_200.code(), response.getStatus());

        List<PodTypeDto> podTypes = response.readEntity(new GenericType<>() {
        });
        assertNotNull(podTypes);
        assertEquals(PodType.values().length, podTypes.size());

        PodTypeDto selectedPodType = podTypes.stream()
                .filter(podTypeDto -> podTypeDto.key.equals(expectedPodType.getKey()))
                .findFirst()
                .orElseThrow();

        assertEquals(expectedPodType.getKey(), selectedPodType.key);
        assertEquals(expectedPodType.getLabel(), selectedPodType.label);
    }
}