package jp.co.company.space.api.features.catalog.dto;

import jp.co.company.space.api.features.catalog.domain.CatalogItem;
import jp.co.company.space.api.features.catalog.domain.Nationality;
import jp.co.company.space.api.features.catalog.exception.CatalogError;
import jp.co.company.space.api.features.catalog.exception.CatalogException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.NATIONALITY_KEY_EXAMPLE;
import static jp.co.company.space.api.shared.openApi.Examples.NATIONALITY_LABEL_EXAMPLE;

/**
 * Represents the DTO of {@link Nationality}.
 */
@Schema(name = "Nationality", description = "The details of a nationality.")
public class NationalityDto extends CatalogItemDto {

    /**
     * Creates a {@link NationalityDto} instance based on a {@link Nationality} instance.
     *
     * @param nationality The base for the {@link NationalityDto} instance.
     * @return A {@link NationalityDto} instance.
     */
    public static NationalityDto create(Nationality nationality) throws CatalogException {
        if (nationality == null) {
            throw new CatalogException(CatalogError.NATIONALITY_MISSING);
        }

        return new NationalityDto(nationality.getKey(), nationality.getLabel());
    }

    /**
     * Creates a {@link NationalityDto} instance based on a {@link CatalogItem} instance.
     *
     * @param catalogItem The base for the {@link NationalityDto} instance.
     * @return A {@link NationalityDto} instance.
     */
    public static NationalityDto fromCatalogItem(CatalogItem catalogItem) throws CatalogException {
        if (!(catalogItem instanceof Nationality)) {
            throw new CatalogException(CatalogError.CATALOG_ITEM_NATIONALITY_MISMATCH);
        }

        return create((Nationality) catalogItem);
    }

    @Schema(description = "The key of the nationality.", example = NATIONALITY_KEY_EXAMPLE)
    public String key;

    @Schema(description = "The label of the nationality.", example = NATIONALITY_LABEL_EXAMPLE)
    public String label;

    protected NationalityDto() {
    }

    protected NationalityDto(String key, String label) throws CatalogException {
        if (key == null) {
            throw new CatalogException(CatalogError.MISSING_KEY);
        } else if (label == null) {
            throw new CatalogException(CatalogError.MISSING_LABEL);
        }

        this.key = key;
        this.label = label;
    }
}
