package jp.co.company.space.api.features.voyage.domain;

/**
 * The status of a {@link Voyage}.
 */
public enum VoyageStatus {
    /**
     * The voyage is confirmed and bookings are open.
     */
    SCHEDULED("scheduled"),
    /**
     * The voyage reached its maximum passenger limit and bookings are closed.
     */
    AT_CAPACITY("atCapacity"),
    /**
     * The voyage is under review and awaiting approval.
     */
    PENDING_APPROVAL("pendingApproval"),
    /**
     * The voyage departure time has changed.
     */
    RESCHEDULED("rescheduled"),
    /**
     * The voyage departure is postponed.
     */
    DELAYED("delayed"),
    /**
     * The passengers of the voyage are boarding the space shuttle.
     */
    BOARDING("boarding"),
    /**
     * The space shuttle of the voyage is preparing to launch.
     */
    PREPARING_LAUNCH("preparingLaunch"),
    /**
     * The space shuttle launched and is getting in orbit.
     */
    LAUNCHED("launched"),
    /**
     * The space shuttle is in orbit and en route to its destination.
     */
    IN_TRANSIT("inTransit"),
    /**
     * The space shuttle arrived at its destination.
     */
    LANDED("landed"),
    /**
     * The voyage is finished.
     */
    COMPLETED("completed"),
    /**
     * The voyage is cancelled.
     */
    CANCELLED("cancelled"),
    /**
     * The voyage is aborted.
     */
    ABORTED("aborted");

    /**
     * The translation key for the status.
     */
    private final String localeCode;

    VoyageStatus(String localeCode) {
        this.localeCode = localeCode;
    }

    public String getLocaleCode() {
        return localeCode;
    }
}
