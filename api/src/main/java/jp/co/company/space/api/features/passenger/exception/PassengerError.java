package jp.co.company.space.api.features.passenger.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with passenger error messages.
 */
public enum PassengerError implements DomainError {
    MISSING("passenger.missing", "The passenger is missing."),
    MISSING_PASSENGER_DETAILS("passenger.missingPassengerDetails", "The details for new passenger are missing."),
    MISSING_ID("passenger.missingId", "The ID of the passenger is missing."),
    MISSING_CREATION_DATE("passenger.missingCreationDate", "The creation date of the passenger is missing."),
    MISSING_MEAL_PREFERENCE("passenger.missingMealPreference", "The meal preference of the passenger is missing."),
    MISSING_PACKAGE_TYPE("passenger.missingPackageType", "The package type assigned to the passenger is missing."),
    MISSING_BOOKING("passenger.missingBooking", "The booking of the passenger is missing."),
    MISSING_BOOKING_ID("passenger.missingBookingId", "The booking ID of the passenger is missing."),
    MISSING_VOYAGE("passenger.missingVoyage", "The voyage of the passenger is missing."),
    MISSING_VOYAGE_ID("passenger.missingVoyageId", "The voyage ID of the passenger is missing."),
    MISSING_POD_RESERVATION("passenger.missingPodReservation", "The pod reservation for the passenger is missing."),
    MISSING_POD_RESERVATION_ID("passenger.missingPodReservationId", "The pod reservation ID for the passenger is missing."),

    HAS_POD_RESERVATION("passenger.hasPodReservation", "This passenger already has a pod reservation."),

    FIND_BY_ID("passenger.findById", "Failed to find a passenger with the provided ID."),
    SAVE("passenger.save", "Failed to save a passenger."),
    MERGE("passenger.merge", "Failed to merge a passenger."),
    SAVE_LIST("passenger.saveList", "Failed to save a list of passengers."),
    CREATE("passenger.create", "Failed to create passengers.");

    private final String key;
    private final String description;

    PassengerError(String key, String description) {
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
