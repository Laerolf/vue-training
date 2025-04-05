package jp.co.company.space.api.features.voyage.domain;

import java.math.BigDecimal;
import java.time.Duration;

import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.route.domain.RouteDistanceFactory;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;

/**
 * A POJO calculating the duration of a voyage.
 */
public class VoyageDurationFactory {

    /**
     * Returns a new {@link VoyageDurationFactory} instance.
     * 
     * @param route        The route to calculate the duration with.
     * @param spaceShuttle The space shuttle to calculate the duration with.
     * @return A new {@link VoyageDurationFactory} instance.
     * @throws IllegalArgumentException When the provided route is null.
     * @throws IllegalArgumentException When the provided space shuttle is null.
     */
    public static VoyageDurationFactory create(final Route route, final SpaceShuttle spaceShuttle) {
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

    private VoyageDurationFactory(Route route, SpaceShuttle spaceShuttle) {
        if (route == null) {
            throw new IllegalArgumentException("The route of the voyage duration factory is missing.");
        } else if (spaceShuttle == null) {
            throw new IllegalArgumentException("The space shuttle of the voyage duration factory is missing.");
        }

        this.spaceShuttle = spaceShuttle;
        this.routeDistance = RouteDistanceFactory.create(route).calculate();
    }

    /**
     * Returns the duration in days of a voyage based on its {@link Route} and {@link SpaceShuttle}.
     * 
     * @return A duration in days of a voyage.
     */
    public Duration calculate() {
        long durationInHours = routeDistance.longValue() / spaceShuttle.getModel().getMaxSpeed();
        return Duration.ofHours(durationInHours);
    }
}
