package jp.co.nova.gate.api.features.voyage.domain;

import jp.co.nova.gate.api.features.route.domain.Route;
import jp.co.nova.gate.api.features.route.domain.RouteDistanceFactory;
import jp.co.nova.gate.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.nova.gate.api.features.voyage.exception.VoyageError;
import jp.co.nova.gate.api.features.voyage.exception.VoyageException;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * A POJO calculating the duration of a voyage.
 */
public class VoyageDurationFactory {

    /**
     * Returns a new {@link VoyageDurationFactory}.
     *
     * @param route        The route to calculate the duration with.
     * @param spaceShuttle The space shuttle to calculate the duration with.
     * @return A new {@link VoyageDurationFactory}.
     * @throws IllegalArgumentException When the provided route is null.
     * @throws IllegalArgumentException When the provided space shuttle is null.
     */
    public static VoyageDurationFactory create(final Route route, final SpaceShuttle spaceShuttle) throws VoyageException {
        return new VoyageDurationFactory(route, spaceShuttle);
    }

    /**
     * The computed distance in kilometers for the provided route.
     */
    private final BigDecimal routeDistance;

    /**
     * The space shuttle to calculate the duration with.
     */
    private final SpaceShuttle spaceShuttle;

    protected VoyageDurationFactory(Route route, SpaceShuttle spaceShuttle) throws VoyageException {
        try {
            if (route == null) {
                throw new VoyageException(VoyageError.MISSING_ROUTE);
            } else if (spaceShuttle == null) {
                throw new VoyageException(VoyageError.MISSING_SPACE_SHUTTLE);
            }

            this.spaceShuttle = spaceShuttle;
            this.routeDistance = RouteDistanceFactory.create(route).calculate();
        } catch (VoyageException exception) {
            throw new VoyageException(VoyageError.VOYAGE_DURATION_CALCULATION, exception);
        }
    }

    /**
     * Returns the duration in days of a voyage based on its {@link Route} and {@link SpaceShuttle}.
     *
     * @return A duration in days of a voyage.
     */
    public Duration calculate() throws VoyageException {
        try {
            long durationInHours = routeDistance.longValue() / spaceShuttle.getModel().getMaxSpeed();
            return Duration.ofHours(durationInHours);
        } catch (ArithmeticException exception) {
            throw new VoyageException(VoyageError.VOYAGE_DURATION_CALCULATION);
        }
    }
}
