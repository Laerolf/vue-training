package jp.co.company.space.api.features.pod.exception;

import jp.co.company.space.api.shared.exception.DomainError;

/**
 * An enum with pod reservation error messages.
 */
public enum PodReservationError implements DomainError {
    MISSING("podReservation.missing", "The pod reservation is missing."),
    MISSING_ID("podReservation.missingId", "The ID of the pod reservation is missing."),
    MISSING_CODE("podReservation.missingCode", "The pod code of the pod reservation is missing."),
    MISSING_CREATION_DATE("podReservation.missingCreationDate", "The creation date of the pod reservation is missing."),
    MISSING_PASSENGER("podReservation.missingPassenger", "The passenger of the pod reservation is missing."),
    MISSING_PASSENGER_ID("podReservation.missingPassengerId", "The passenger ID of the pod reservation is missing."),
    MISSING_VOYAGE("podReservation.missingVoyage", "The voyage of the pod reservation is missing."),
    MISSING_VOYAGE_ID("podReservation.missingVoyageId", "The voyage ID of the pod reservation is missing."),
    MISSING_RESERVATIONS("podReservation.missingReservations", "The existing reservations are missing."),

    RESERVED("podReservation.reserved", "The requested pod is already reserved."),
    MISMATCHED_PACKAGE_TYPE("podReservation.mismatchedPackageType", "The requested pod is not available for the passenger's package type."),
    FULLY_BOOKED("podReservation.fullyBooked", "The space shuttle is full."),

    FIND_BY_ID("podReservation.findById", "Failed to find a pod reservation for the provided ID."),
    GET_ALL_BY_SPACE_SHUTTLE_AND_VOYAGE("podReservation.getAllBySpaceShuttleAndVoyage", "Failed to get all pod reservations for the provided space shuttle and voyage."),
    SAVE("podReservation.save", "Failed to save a pod reservation."),
    MERGE("podReservation.merge", "Failed to merge a pod reservation."),
    SAVE_LIST("podReservation.saveList", "Failed to save pod reservations.");

    private final String key;
    private final String description;

    PodReservationError(String key, String description) {
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
