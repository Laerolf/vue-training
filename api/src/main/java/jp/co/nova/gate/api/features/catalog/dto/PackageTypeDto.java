package jp.co.nova.gate.api.features.catalog.dto;

import jp.co.nova.gate.api.features.catalog.domain.CatalogItem;
import jp.co.nova.gate.api.features.catalog.domain.PackageType;
import jp.co.nova.gate.api.features.catalog.exception.CatalogError;
import jp.co.nova.gate.api.features.catalog.exception.CatalogException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.nova.gate.api.shared.openApi.Examples.PACKAGE_TYPE_KEY_EXAMPLE;
import static jp.co.nova.gate.api.shared.openApi.Examples.PACKAGE_TYPE_LABEL_EXAMPLE;

/**
 * A POJO representing a DTO of a {@link PackageType}.
 */
@Schema(name = "PackageType", description = "The details of a package type.")
public class PackageTypeDto extends CatalogItemDto {

    /**
     * Creates a {@link PackageTypeDto} based on a {@link PackageType}.
     *
     * @param packageType The base for the {@link PackageTypeDto}.
     * @return A {@link PackageTypeDto}.
     */
    public static PackageTypeDto create(PackageType packageType) throws CatalogException {
        if (packageType == null) {
            throw new CatalogException(CatalogError.PACKAGE_TYPE_MISSING);
        }

        return new PackageTypeDto(packageType.getKey(), packageType.getLabel());
    }

    /**
     * Creates a {@link PackageTypeDto} based on a {@link CatalogItem}.
     *
     * @param catalogItem The base for the {@link PackageTypeDto}.
     * @return A {@link PackageTypeDto}.
     */
    public static PackageTypeDto fromCatalogItem(CatalogItem catalogItem) throws CatalogException {
        if (!(catalogItem instanceof PackageType)) {
            throw new CatalogException(CatalogError.CATALOG_ITEM_PACKAGE_TYPE_MISMATCH);
        }

        return create((PackageType) catalogItem);
    }

    @Schema(description = "The key of the package type.", example = PACKAGE_TYPE_KEY_EXAMPLE)
    public String key;

    @Schema(description = "The label of the package type.", example = PACKAGE_TYPE_LABEL_EXAMPLE)
    public String label;

    protected PackageTypeDto() {
    }

    protected PackageTypeDto(String key, String label) throws CatalogException {
        if (key == null) {
            throw new CatalogException(CatalogError.MISSING_KEY);
        } else if (label == null) {
            throw new CatalogException(CatalogError.MISSING_LABEL);
        }

        this.key = key;
        this.label = label;
    }
}
