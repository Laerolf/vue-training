package jp.co.company.space.utils.features.voyage;

import jp.co.company.space.api.features.location.domain.Location;
import jp.co.company.space.api.features.route.domain.Route;
import jp.co.company.space.api.features.spaceShuttle.domain.SpaceShuttle;
import jp.co.company.space.api.features.spaceShuttleModel.domain.SpaceShuttleModel;
import jp.co.company.space.api.features.spaceStation.domain.SpaceStation;
import jp.co.company.space.api.features.voyage.domain.Voyage;
import jp.co.company.space.api.features.voyage.exception.VoyageException;
import jp.co.company.space.utils.features.location.LocationTestDataBuilder;
import jp.co.company.space.utils.features.route.RouteTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttle.SpaceShuttleTestDataBuilder;
import jp.co.company.space.utils.features.spaceShuttleModel.SpaceShuttleModelTestDataBuilder;
import jp.co.company.space.utils.features.spaceStation.SpaceStationTestDataBuilder;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * A test data builder that creates a {@link Voyage} instance for testing purposes.
 */
public class VoyageTestDataBuilder {

    private static final SpaceStationTestDataBuilder SPACE_STATION_BUILDER = new SpaceStationTestDataBuilder();
    private static final LocationTestDataBuilder LOCATION_BUILDER = new LocationTestDataBuilder();

    private static final Location DEFAULT_ORIGIN_LOCATION = LOCATION_BUILDER.create();
    private static final Location DEFAULT_DESTINATION_LOCATION = LOCATION_BUILDER.withName("Mars").withLatitude(0).withLongitude(13.1).withRadialDistance(1.5).create();

    private static final SpaceStation DEFAULT_ORIGIN_SPACE_STATION = SPACE_STATION_BUILDER.create(DEFAULT_ORIGIN_LOCATION);
    private static final SpaceStation DEFAULT_DESTINATION_SPACE_STATION = SPACE_STATION_BUILDER.create(DEFAULT_DESTINATION_LOCATION);

    private static final SpaceShuttleModel DEFAULT_SHUTTLE_MODEL = new SpaceShuttleModelTestDataBuilder().create();

    private static final ZonedDateTime DEFAULT_DEPARTURE_DATE = ZonedDateTime.now();
    private static final Route DEFAULT_ROUTE = new RouteTestDataBuilder().create(DEFAULT_ORIGIN_SPACE_STATION, DEFAULT_DESTINATION_SPACE_STATION, DEFAULT_SHUTTLE_MODEL);
    private static final SpaceShuttle DEFAULT_SPACE_SHUTTLE = new SpaceShuttleTestDataBuilder().withModel(DEFAULT_SHUTTLE_MODEL).create();

    private ZonedDateTime departureDate;
    private Route route;
    private SpaceShuttle spaceShuttle;

    public VoyageTestDataBuilder() {
    }

    public VoyageTestDataBuilder withDepartureDate(ZonedDateTime departureDate) {
        this.departureDate = departureDate;
        return this;
    }

    public VoyageTestDataBuilder withRoute(Route route) {
        this.route = route;
        return this;
    }

    public VoyageTestDataBuilder withSpaceShuttle(SpaceShuttle spaceShuttle) {
        this.spaceShuttle = spaceShuttle;
        return this;
    }

    public Voyage create() throws VoyageException {
        return Voyage.create(
                Optional.ofNullable(departureDate).orElse(DEFAULT_DEPARTURE_DATE),
                Optional.ofNullable(route).orElse(DEFAULT_ROUTE),
                Optional.ofNullable(spaceShuttle).orElse(DEFAULT_SPACE_SHUTTLE)
        );
    }
}
