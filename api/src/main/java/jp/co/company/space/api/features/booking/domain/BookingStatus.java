package jp.co.company.space.api.features.booking.domain;

/**
 * The status of a {@link Booking}.
 */
public enum BookingStatus {
    /**
     * The creation of the booking has started and has not been confirmed yet.
     */
    DRAFT("draft"),
    /**
     * The booking has been created and has not been paid yet.
     */
    CREATED("created"),
    /**
     * The booking has been paid.
     */
    PAID("paid"),
    /**
     * The voyage of the booking has ended.
     */
    COMPLETE("complete");

    /**
     * The prefix used for the status's label.
     */
    public final static String LABEL_PREFIX = "bookingStatus";

    /**
     * The key for the status.
     */
    private final String key;

    /**
     * The label of the status, used for translating.
     */
    private final String label;

    BookingStatus(String key) {
        this.key = key;
        this.label = String.join(".", LABEL_PREFIX, this.key);
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }
}
