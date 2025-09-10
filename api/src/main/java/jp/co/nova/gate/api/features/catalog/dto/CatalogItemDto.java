package jp.co.nova.gate.api.features.catalog.dto;

import jp.co.nova.gate.api.features.catalog.domain.*;
import jp.co.nova.gate.api.features.catalog.exception.CatalogError;
import jp.co.nova.gate.api.features.catalog.exception.CatalogException;

/**
 * Represents a catalog item DTO.
 */
public class CatalogItemDto {
    /**
     * Converts a {@link CatalogItem} to {@link CatalogItemDto}.
     *
     * @param catalogItem The base of the {@link CatalogItemDto}.
     * @return A {@link CatalogItemDto}.
     */
    public static CatalogItemDto fromCatalogItem(CatalogItem catalogItem) throws CatalogException {
        return switch (catalogItem) {
            case Gender ignored -> GenderDto.fromCatalogItem(catalogItem);
            case MealPreference ignored -> MealPreferenceDto.fromCatalogItem(catalogItem);
            case Nationality ignored -> NationalityDto.fromCatalogItem(catalogItem);
            case PackageType ignored -> PackageTypeDto.fromCatalogItem(catalogItem);
            case PodType ignored -> PodTypeDto.fromCatalogItem(catalogItem);
            case null, default -> throw new CatalogException(CatalogError.CATALOG_ITEM_MISMATCH);
        };
    }

    protected CatalogItemDto() {
    }
}
