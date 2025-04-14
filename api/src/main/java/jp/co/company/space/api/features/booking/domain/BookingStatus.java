package jp.co.company.space.api.features.booking.domain;

/**
 * The status of a {@link Booking} instance.
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
     * The translation key for the status.
     */
    private final String localeCode;

    BookingStatus(String localeCode) {
        this.localeCode = localeCode;
    }

    public String getLocaleCode() {
        return localeCode;
    }
}
