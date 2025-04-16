package jp.co.company.space.api.features.catalog.dto;

import jp.co.company.space.api.features.catalog.domain.PackageType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * A POJO representing a DTO of a {@link PackageType}.
 */
@Schema(description = "The details of a package type.")
public class PackageTypeDto {

    /**
     * Creates a {@link PackageTypeDto} instance based on a {@link PackageType} instance.
     *
     * @param packageType The base for the {@link PackageTypeDto} instance.
     * @return A {@link PackageTypeDto} instance.
     */
    public static PackageTypeDto create(PackageType packageType) {
        if (packageType == null) {
            throw new IllegalArgumentException("The base package type of the package type is missing.");
        }

        return new PackageTypeDto(packageType.getKey());
    }

    @Schema(description = "The key of the package type.")
    public String key;

    protected PackageTypeDto() {
    }

    protected PackageTypeDto(String key) {
        if (key == null) {
            throw new IllegalArgumentException("The key of the package type is missing.");
        }

        this.key = key;
    }
}
