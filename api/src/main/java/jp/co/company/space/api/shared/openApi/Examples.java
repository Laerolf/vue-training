package jp.co.company.space.api.shared.openApi;

/**
 * A collection of Open API Examples.
 */
public class Examples {
    // Shared
    public static final String ID_EXAMPLE = "1";
    public static final String CREATION_DATE_EXAMPLE = "2025-04-14T08:49:20.507334+09:00";

    // Location
    public static final String LOCATION_ID_EXAMPLE = "00000000-0000-1000-8000-000000000001";
    public static final String LOCATION_NAME_EXAMPLE = "Earth";
    public static final String LOCATION_LATITUDE_EXAMPLE = "0.0";
    public static final String LOCATION_LONGITUDE_EXAMPLE = "0.0";
    public static final String LOCATION_RADIAL_DISTANCE_EXAMPLE = "27000";

    // Location characteristics
    public static final String LOCATION_CHARACTERISTICS_KEY_EXAMPLE = "silent";
    public static final String LOCATION_CHARACTERISTICS_LABEL_EXAMPLE = "planetCharacteristic.silent";

    // Space station
    public static final String SPACE_STATION_ORIGIN_ID_EXAMPLE = "00000000-0000-1000-8000-000000000003";
    public static final String SPACE_STATION_DESTINATION_ID_EXAMPLE = "00000000-0000-1000-8000-000000000012";
    public static final String SPACE_STATION_NAME_EXAMPLE = "Tanegashima Space Center";
    public static final String SPACE_STATION_CODE_EXAMPLE = "TNS";
    public static final String SPACE_STATION_COUNTRY_EXAMPLE = "Japan";

    // Booking
    public static final String BOOKING_STATUS_EXAMPLE = "bookingStatus.created";
    public static final String BOOKING_PASSENGER_ID_EXAMPLE = "[1]";

    // Voyage
    public static final String VOYAGE_ID_EXAMPLE = "5f485136-20a8-41f3-9073-4156d32c9c36";
    public static final String VOYAGE_DEPARTURE_DATE_EXAMPLE = "2050-02-22T08:49:20.507334+09:00";
    public static final String VOYAGE_ARRIVAL_DATE_EXAMPLE = "2050-03-21T17:49:20.507334+09:00";
    public static final String VOYAGE_DURATION_EXAMPLE = "2365200";
    public static final String VOYAGE_STATUS_EXAMPLE = "voyageStatus.scheduled";

    // Route
    public static final String ROUTE_ID_EXAMPLE = "08bd4df1-97eb-4a9a-aa21-d9b7bed299a3";
    public static final String ROUTE_ORIGIN_ID_EXAMPLE = "00000000-0000-1000-8000-000000000003";
    public static final String ROUTE_DESTINATION_ID_EXAMPLE = "00000000-0000-1000-8000-000000000012";

    public static final String ROUTE_DESTINATION_EXAMPLE = "{ \"code\": \"RHV\", \"country\": \"null\", \"id\": \"872123e1-81e3-424b-8e40-607e932e910a\", \"location\": { \"id\": \"4\", \"latitude\": 0, \"longitude\": 13.1, \"name\": \"Mars\", \"radialDistance\": 1.5},\"name\": \"Red Haven - Mars\"}";

    // Space shuttle
    public static final String SPACE_SHUTTLE_ID_EXAMPLE = "00000000-0000-1000-8000-000000000003";
    public static final String SPACE_SHUTTLE_NAME_EXAMPLE = "From Hell with love";

    // Space shuttle model
    public static final String SPACE_SHUTTLE_MODEL_ID_EXAMPLE = "00000000-0000-1000-8000-000000000003";
    public static final String SPACE_SHUTTLE_MODEL_NAME_EXAMPLE = "Morningstar X-666";
    public static final String SPACE_SHUTTLE_MODEL_MAX_CAPACITY_EXAMPLE = "666";
    public static final String SPACE_SHUTTLE_MODEL_MAX_SPEED_EXAMPLE = "90000";

    // Pod
    public static final String POD_CODE_EXAMPLE = "S100001";
    public static final String POD_TYPE_EXAMPLE = "standardPod";
    public static final String POD_DECK_NUMBER_EXAMPLE = "1";
    public static final String POD_ROW_NUMBER_EXAMPLE = "1";
    public static final String POD_COLUMN_NUMBER_EXAMPLE = "1";
    public static final String POD_STATUS_EXAMPLE = "podStatus.available";

    // User
    public static final String USER_LAST_NAME_EXAMPLE = "Jekyll";
    public static final String USER_FIRST_NAME_EXAMPLE = "Henry";
    public static final String USER_EMAIL_ADDRESS_EXAMPLE = "jekyll.henry@test.test";
    public static final String USER_PASSWORD_EXAMPLE = "test";

    // Passenger
    public static final String PASSENGER_MIDDLE_NAME_EXAMPLE = "Edward";
    public static final String PASSENGER_BIRTHDATE_EXAMPLE = "1835-11-12";
    public static final String PASSENGER_PASSPORT_NUMBER_EXAMPLE = "752104983";

    // Package type
    public static final String PACKAGE_TYPE_KEY_EXAMPLE = "economy";
    public static final String PACKAGE_TYPE_LABEL_EXAMPLE = "packageType.economy";

    // Pod type
    public static final String POD_TYPE_KEY_EXAMPLE = "standardPod";
    public static final String POD_TYPE_LABEL_EXAMPLE = "podType.standardPod";

    // Meal preference
    public static final String MEAL_PREFERENCE_KEY_EXAMPLE = "standard";
    public static final String MEAL_PREFERENCE_LABEL_EXAMPLE = "mealPreference.standard";
    public static final String MEAL_PREFERENCE_ADDITIONAL_COST_EXAMPLE = "0";
    public static final String MEAL_PREFERENCE_AVAILABLE_FROM_EXAMPLE = PACKAGE_TYPE_KEY_EXAMPLE;
    public static final String MEAL_PREFERENCE_FREE_FROM_EXAMPLE = PACKAGE_TYPE_KEY_EXAMPLE;

    // Nationality
    public static final String NATIONALITY_CODE_EXAMPLE = "GB";
    public static final String NATIONALITY_EXAMPLE = "United Kingdom";
    public static final String NATIONALITY_KEY_EXAMPLE = NATIONALITY_CODE_EXAMPLE;
    public static final String NATIONALITY_LABEL_EXAMPLE = "nationality.gb";

    // Gender
    public static final String GENDER_EXAMPLE = "male";
    public static final String GENDER_KEY_EXAMPLE = "male";
    public static final String GENDER_LABEL_EXAMPLE = "gender.male";

    // Error
    public static final String ERROR_KEY_EXAMPLE = "error.theRoofIsOnFire";
    public static final String ERROR_KEY_MESSAGE = "Something went terribly wrong.";
    public static final String ERROR_PROPERTIES = "{\"id\": \"66600000-0000-1000-8000-000000000012\"}";
}
