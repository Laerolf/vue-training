package jp.co.company.space.api.features.booking.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with booking error messages.
 */
public enum BookingError implements DomainError {
    NEW_MISSING_USER("newBooking.missingUser", "The new booking's user is missing."),
    NEW_MISSING_VOYAGE("newBooking.missingVoyage", "The new booking's voyage is missing."),
    NEW_MISSING_CREATION_FORM("newBooking.missingCreationForm", "The form for a new booking is missing."),
    NEW_USER_NOT_FOUND("newBooking.userNotFound", "The new booking's user could not be found."),
    NEW_VOYAGE_NOT_FOUND("newBooking.voyageNotFound", "The new booking's voyage could not be found."),

    MISSING("booking.missing", "The booking is missing."),
    MISSING_ID("booking.missingId", "The ID of the booking is missing."),
    MISSING_CREATION_DATE("booking.missingCreationDate", "The creation date of the booking is missing."),
    MISSING_STATUS("booking.missingStatus", "The status of the booking is missing."),
    MISSING_USER("booking.missingUser", "The user of the booking is missing."),
    MISSING_USER_ID("booking.missingUserId", "The user ID of the booking is missing."),
    MISSING_VOYAGE("booking.missingVoyage", "The voyage of the booking is missing."),
    MISSING_VOYAGE_ID("booking.missingVoyageId", "The voyage ID of the booking is missing."),
    MISSING_PASSENGERS("booking.missingPassengers", "The passengers of the booking are missing."),
    MISSING_PASSENGER_IDS("booking.missingPassengerIds", "The passenger IDs of the booking are missing."),
    INVALID_PASSENGER_COUNT("booking.invalidPassengerCount", "A booking needs at least one passenger."),

    CREATE("booking.create", "Unable to create a booking."),
    FIND_BY_ID("booking.findById", "Failed to find the booking with the provided ID."),
    FIND_BY_USER_ID("booking.findByUserId", "Failed to find the booking with the provided user ID."),
    FIND_BY_VOYAGE_ID("booking.findByVoyageId", "Failed to find the booking with the provided voyage ID."),
    SAVE("booking.save", "Failed to save the booking."),
    SAVE_LIST("booking.saveList", "Failed to save a list of bookings."),
    MERGE("booking.merge", "Failed to merge the booking.");

    private final String key;
    private final String description;

    BookingError(String key, String description) {
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
