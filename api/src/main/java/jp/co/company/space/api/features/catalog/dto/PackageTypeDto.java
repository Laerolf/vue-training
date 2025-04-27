package jp.co.company.space.api.features.catalog.dto;

import jp.co.company.space.api.features.catalog.domain.PackageType;
import jp.co.company.space.api.features.catalog.exception.CatalogError;
import jp.co.company.space.api.features.catalog.exception.CatalogException;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import static jp.co.company.space.api.shared.openApi.Examples.PACKAGE_TYPE_KEY_EXAMPLE;

/**
 * A POJO representing a DTO of a {@link PackageType}.
 */
@Schema(name = "PackageType", description = "The details of a package type.")
public class PackageTypeDto {

    /**
     * Creates a {@link PackageTypeDto} instance based on a {@link PackageType} instance.
     *
     * @param packageType The base for the {@link PackageTypeDto} instance.
     * @return A {@link PackageTypeDto} instance.
     * @throws CatalogException When the package type is missing.
     */
    public static PackageTypeDto create(PackageType packageType) throws CatalogException {
        if (packageType == null) {
            throw new CatalogException(CatalogError.PACKAGE_TYPE_MISSING);
        }

        return new PackageTypeDto(packageType.getKey());
    }

    @Schema(description = "The key of the package type.", example = PACKAGE_TYPE_KEY_EXAMPLE)
    public String key;

    protected PackageTypeDto() {
    }

    protected PackageTypeDto(String key) throws CatalogException {
        if (key == null) {
            throw new CatalogException(CatalogError.PACKAGE_TYPE_MISSING_KEY);
        }

        this.key = key;
    }
}
