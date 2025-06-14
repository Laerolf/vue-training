package jp.co.company.space.api.features.catalog.dto;

import jp.co.company.space.api.features.catalog.domain.CatalogItem;
import jp.co.company.space.api.features.catalog.domain.PodType;
import jp.co.company.space.api.features.catalog.exception.CatalogError;
import jp.co.company.space.api.features.catalog.exception.CatalogException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.POD_TYPE_KEY_EXAMPLE;
import static jp.co.company.space.api.shared.openApi.Examples.POD_TYPE_LABEL_EXAMPLE;

/**
 * A POJO representing a DTO of a {@link PodType}.
 */
@Schema(name = "PodType", description = "The details of a pod type.")
public class PodTypeDto extends CatalogItemDto {

    /**
     * Creates a {@link PodTypeDto} instance based on a {@link PodType} instance.
     *
     * @param podType The base for the {@link PodTypeDto} instance.
     * @return A {@link PodTypeDto} instance.
     */
    public static PodTypeDto create(PodType podType) throws CatalogException {
        if (podType == null) {
            throw new CatalogException(CatalogError.PACKAGE_TYPE_MISSING);
        }

        return new PodTypeDto(podType.getKey(), podType.getLabel());
    }

    /**
     * Creates a {@link PodTypeDto} instance based on a {@link CatalogItem} instance.
     *
     * @param catalogItem The base for the {@link PodTypeDto} instance.
     * @return A {@link PodTypeDto} instance.
     */
    public static PodTypeDto fromCatalogItem(CatalogItem catalogItem) throws CatalogException {
        if (!(catalogItem instanceof PodType)) {
            throw new CatalogException(CatalogError.CATALOG_ITEM_POD_TYPE_MISMATCH);
        }

        return create((PodType) catalogItem);
    }

    @Schema(description = "The key of the pod type.", example = POD_TYPE_KEY_EXAMPLE)
    public String key;

    @Schema(description = "The label of the pod type.", example = POD_TYPE_LABEL_EXAMPLE)
    public String label;

    protected PodTypeDto() {
    }

    protected PodTypeDto(String key, String label) throws CatalogException {
        if (key == null) {
            throw new CatalogException(CatalogError.MISSING_KEY);
        } else if (label == null) {
            throw new CatalogException(CatalogError.MISSING_LABEL);
        }

        this.key = key;
        this.label = label;
    }
}
