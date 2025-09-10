package jp.co.nova.gate.api.features.route.domain;

import jp.co.nova.gate.api.features.location.domain.Location;
import jp.co.nova.gate.api.features.route.exception.RouteError;
import jp.co.nova.gate.api.features.route.exception.RouteException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * A POJO calculating the <a href="https://en.wikipedia.org/wiki/Euclidean_distance">Euclidean</a> kilometer distance in 3D space for a route between two points.
 */
public class RouteDistanceFactory {

    /**
     * Conversion factor: 1 Astronomical Unit (AU) = 149,597,870.7 kilometers.
     */
    private static final BigDecimal AU_TO_KM = new BigDecimal("149597870.7");

    /**
     * Defines the precision and rounding mode for calculations.
     */
    private static final MathContext MATH_CONTEXT = new MathContext(25, RoundingMode.HALF_UP);

    /**
     * Returns a new {@link RouteDistanceFactory}.
     *
     * @param route The route to calculate the distance for.
     * @return A new {@link RouteDistanceFactory}.
     * @throws RouteException if the provided route is null.
     */
    public static RouteDistanceFactory create(final Route route) throws RouteException {
        return new RouteDistanceFactory(route);
    }

    /**
     * The route to calculate the distance for.
     */
    private final Route route;

    public RouteDistanceFactory(final Route route) throws RouteException {
        if (route == null) {
            throw new RouteException(RouteError.ROUTE_DISTANCE_MISSING_ROUTE);
        }

        this.route = route;
    }

    /**
     * Computes the Euclidean distance in kilometers between two points in 3D space.
     *
     * @return The Euclidean distance in kilometers.
     */
    public BigDecimal calculate() {
        BigDecimal[] origin = toCartesian(route.getOrigin().getLocation());
        BigDecimal[] destination = toCartesian(route.getDestination().getLocation());

        BigDecimal dx = destination[0].subtract(origin[0]).pow(2, MATH_CONTEXT);
        BigDecimal dy = destination[1].subtract(origin[1]).pow(2, MATH_CONTEXT);
        BigDecimal dz = destination[2].subtract(origin[2]).pow(2, MATH_CONTEXT);

        BigDecimal euclideanDistance = dx.add(dy).add(dz).sqrt(MATH_CONTEXT);
        return convertAuToKm(euclideanDistance);
    }

    /**
     * Converts degrees to radians.
     *
     * @param degrees The angle in degrees.
     * @return The angle in radians.
     */
    private BigDecimal toRadians(final double degrees) {
        return BigDecimal.valueOf(Math.toRadians(degrees));
    }

    /**
     * Converts a distance from Astronomical Units (AU) to kilometers.
     *
     * @param au The distance in AU.
     * @return The equivalent distance in kilometers.
     */
    private BigDecimal convertAuToKm(final BigDecimal au) {
        return au.multiply(AU_TO_KM, MATH_CONTEXT);
    }

    /**
     * Converts a location's ecliptic coordinates (longitude, latitude, radial distance) into Cartesian coordinates.
     *
     * @param location The celestial location.
     * @return A double array {x, y, z} representing Cartesian coordinates.
     */
    private BigDecimal[] toCartesian(final Location location) {
        BigDecimal longitudeRad = toRadians(location.getLongitude());
        BigDecimal latitudeRad = toRadians(location.getLatitude());

        BigDecimal r = BigDecimal.valueOf(location.getRadialDistance());
        BigDecimal cosLat = BigDecimal.valueOf(StrictMath.cos(latitudeRad.doubleValue()));

        BigDecimal x = r.multiply(cosLat, MATH_CONTEXT).multiply(BigDecimal.valueOf(StrictMath.cos(longitudeRad.doubleValue())), MATH_CONTEXT);

        BigDecimal y = r.multiply(cosLat, MATH_CONTEXT).multiply(BigDecimal.valueOf(StrictMath.sin(longitudeRad.doubleValue())), MATH_CONTEXT);

        BigDecimal z = r.multiply(BigDecimal.valueOf(StrictMath.sin(latitudeRad.doubleValue())), MATH_CONTEXT);

        return new BigDecimal[]{x, y, z};
    }
}
