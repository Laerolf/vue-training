package jp.co.nova.gate.api.features.catalog.dto;

import jp.co.nova.gate.api.features.catalog.domain.CatalogItem;
import jp.co.nova.gate.api.features.catalog.domain.Gender;
import jp.co.nova.gate.api.features.catalog.exception.CatalogError;
import jp.co.nova.gate.api.features.catalog.exception.CatalogException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.nova.gate.api.shared.openApi.Examples.GENDER_KEY_EXAMPLE;
import static jp.co.nova.gate.api.shared.openApi.Examples.GENDER_LABEL_EXAMPLE;

/**
 * Represents the DTO of {@link Gender}.
 */
@Schema(name = "Gender", description = "The details of a gender.")
public class GenderDto extends CatalogItemDto {

    /**
     * Creates a {@link GenderDto} based on a {@link Gender}.
     *
     * @param gender The base for the {@link GenderDto}.
     * @return A {@link GenderDto}.
     */
    public static GenderDto create(Gender gender) throws CatalogException {
        if (gender == null) {
            throw new CatalogException(CatalogError.GENDER_MISSING);
        }

        return new GenderDto(gender.getKey(), gender.getLabel());
    }

    /**
     * Creates a {@link GenderDto} based on a {@link CatalogItem}.
     *
     * @param catalogItem The base for the {@link GenderDto}.
     * @return A {@link GenderDto}.
     */
    public static GenderDto fromCatalogItem(CatalogItem catalogItem) throws CatalogException {
        if (!(catalogItem instanceof Gender)) {
            throw new CatalogException(CatalogError.CATALOG_ITEM_GENDER_MISMATCH);
        }

        return create((Gender) catalogItem);
    }

    @Schema(description = "The key of the gender.", example = GENDER_KEY_EXAMPLE)
    public String key;

    @Schema(description = "The label of the gender.", example = GENDER_LABEL_EXAMPLE)
    public String label;

    protected GenderDto() {
    }

    protected GenderDto(String key, String label) throws CatalogException {
        if (key == null) {
            throw new CatalogException(CatalogError.MISSING_KEY);
        } else if (label == null) {
            throw new CatalogException(CatalogError.MISSING_LABEL);
        }

        this.key = key;
        this.label = label;
    }
}
