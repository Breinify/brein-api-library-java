package com.brein.sample;

import com.brein.api.BreinTemporalData;
import com.brein.api.Breinify;
import com.brein.domain.results.BreinTemporalDataResult;
import com.brein.domain.results.temporaldataparts.BreinEventResult;
import com.brein.domain.results.temporaldataparts.BreinHolidayResult;
import com.brein.domain.results.temporaldataparts.BreinLocationResult;
import com.brein.domain.results.temporaldataparts.BreinWeatherResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

public class Sample {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private Sample() {
        /*
         * Utility classes, which are a collection of static members,
         * are not meant to be instantiated.
         */
    }

    public static void resolveIpAddress(final String ipAddress) {
        final BreinTemporalDataResult result = new BreinTemporalData()
                .setLookUpIpAddress(ipAddress)
                .execute();
        final BreinLocationResult location = result.getLocation();

        if (location == null) {
            System.out.println(String.format("-> %s could not be resolved.", ipAddress));
        } else {
            System.out.println(String.format("-> %s is located in %s (%s).",
                    ipAddress, location.getCity(), location.getState()));
        }
    }

    public static void resolveLatLon(final double lat, final double lon) {
        final BreinTemporalDataResult result = new BreinTemporalData()
                .setLatitude(lat)
                .setLongitude(lon)
                .execute();
        final BreinLocationResult location = result.getLocation();

        if (location == null) {
            System.out.println(String.format("-> (%6.4f, %6.4f) could not be resolved.", lat, lon));
        } else {
            System.out.println(String.format("-> (%6.4f, %6.4f) is in %s (%s).",
                    lat, lon, location.getCity(), location.getState()));
        }
    }

    public static void resolveText(final String text) {
        final BreinTemporalDataResult result = new BreinTemporalData()
                .setLocation(text)
                .execute();
        final BreinLocationResult location = result.getLocation();

        if (location == null) {
            System.out.println(String.format("-> '%s' could not be resolved.", text));
        } else {
            System.out.println(String.format("-> '%s' is resolved to %s (%s).",
                    text, location.getCity(), location.getState()));
        }
    }

    public static void resolveFull() {
        final BreinTemporalDataResult result = new BreinTemporalData()
                .setLocalDateTime()
                .execute();

        System.out.println(result);

        final ZonedDateTime zonedDateTime = result.getLocalDateTime();
        final List<BreinEventResult> events = result.getEvents();
        final List<BreinHolidayResult> holidays = result.getHolidays();
        final BreinWeatherResult weather = result.getWeather();
        final BreinLocationResult location = result.getLocation();

        final String weekDay = zonedDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);

        String text = "";

        text += "Today is " + b(weekDay) + ", which is a ";
        text += (weekDay.toLowerCase().startsWith("s") ? "week-end" : "week-day") + ". ";
        if (holidays.isEmpty()) {
            text += "There aren't any holidays/special day(s) today, ";
            text += "but if there would be they would be shown ";
        } else {
            final int rnd = new Random().nextInt(holidays.size());
            text += "Today is/are " + b(holidays.size()) + " holiday(s)/special days ";
            text += "(e.g., " + holidays.get(rnd).getName() + ") ";
        }
        text += "and the timezone is " + b(zonedDateTime.getZone()) + ". ";

        text += "There are " + b(events.size()) + " different events in " + b(location.getCity()) + " today ";
        if (events.isEmpty()) {
            text += "(which would be available, if there are any). ";
        } else {
            final int rnd = new Random().nextInt(events.size());
            text += "(e.g., " + events.get(rnd).getName() + "). ";
        }

        if (weather != null) {
            text += "The data contains up to 12 weather changes ";
            text += "(currently: " + b(weather.getDescription()) + " ";
            text += "with a temperature of " + b(Math.round(weather.getTemperatureCelsius()) + " °C") + ", i.e., ";
            text += b(Math.round(weather.getTemperatureFahrenheit()) + " °F") + ".";
        }

        // do some formatting
        final String[] words = text.split(" ");
        int size = 0;
        for (final String word : words) {
            size += word.length() + 1;
            if (size > 70) {
                System.out.println("");
                size = word.length() + 1;
            }

            System.out.print(word + " ");
        }
    }

    private static String b(final Object text) {
        return ANSI_BLACK + text + ANSI_RESET;
    }

    public static void main(final String[] args) throws IOException {
        final String apiKey;
        final String secret;

        final File propertiesFile = new File("./api.properties");
        if (propertiesFile.exists()) {
            final Properties properties = new Properties();
            properties.load(new FileInputStream(propertiesFile));

            apiKey = String.class.cast(properties.get("apikey"));
            secret = String.class.cast(properties.get("secret"));

            System.out.println("Using apiKey: " + apiKey);
        } else {
            System.out.print("Please enter your api-key: ");
            apiKey = System.console().readLine();
            System.out.print("Please enter your secret (blank if secret should not be used): ");
            secret = System.console().readLine();
        }

        System.out.println("");

        // initialize the system
        Breinify.setConfig(apiKey.trim(), secret == null ? null : (secret.trim().isEmpty() ? null : secret.trim()));

        // resolve ipAddress, as example
        resolveIpAddress("204.28.127.66");

        System.out.println("");

        // resolve coordinates
        resolveLatLon(40.7608, -111.8910);
        resolveLatLon(39.025646, -77.029900);
        resolveLatLon(43.002476, -87.919212);

        System.out.println("");

        // resolve text
        resolveText("Las Vegas");
        resolveText("Grand Canyon");
        resolveText("The Big Apple");

        System.out.println("");

        resolveFull();

        System.out.println("");

        // shut the system down
        Breinify.shutdown();
    }
}
