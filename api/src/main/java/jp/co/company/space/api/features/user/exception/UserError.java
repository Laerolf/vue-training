package jp.co.company.space.api.features.user.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with user error messages.
 */
public enum UserError implements DomainError {
    MISSING("user.missing", "A user is missing."),
    MISSING_ID("user.missingId", "The ID of a user is missing."),
    MISSING_LAST_NAME("user.missingLastName", "The last name of a user is missing."),
    MISSING_FIRST_NAME("user.missingFirstName", "The first name of a user is missing."),
    MISSING_EMAIL_ADDRESS("user.missingEmailAddress", "The email address of a user is missing."),
    MISSING_PASSWORD("user.missingPassword", "The password of a user is missing."),

    NEW_MISSING_ID("newUser.missingId", "The ID for a new user is missing."),
    NEW_MISSING_LAST_NAME("newUser.missingLastName", "The last name for a new user is missing."),
    NEW_MISSING_FIRST_NAME("newUser.missingFirstName", "The first name for a new user is missing."),
    NEW_MISSING_EMAIL_ADDRESS("newUser.missingEmailAddress", "The email address for a new user is missing."),
    NEW_MISSING_PASSWORD("newUser.missingPassword", "The password for a new user is missing."),

    CREATE("user.create", "Unable to create a user."),

    FIND_BY_ID("user.findById", "Failed to find a user with the provided ID."),
    FIND_BY_EMAIL_ADDRESS("user.findByEmailAddress", "Failed to find a user with the provided email address."),
    GET_ALL("user.getAll", "Failed to get all existing users."),
    SAVE("user.save", "Failed to save a user."),
    MERGE("user.merge", "Failed to merge a user."),
    SAVE_LIST("user.saveList", "Failed to save space stations.");

    private final String key;
    private final String description;

    UserError(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
