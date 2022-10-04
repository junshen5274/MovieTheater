package com.jpmc.theater;

import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;

/**
 * Utility class for the movie theater system
 */
public class Utility {

    public static final int OBJECT_MOVIE = 1;
    public static final int OBJECT_CUSTOMER = 2;
    public static final int OBJECT_RESERVATION = 3;
    
    /**
     * Round the double to two places
     * 
     * @param d
     * @return
     */
    public static double roundToTwoPlaces(double d) {
        return Math.round(d * 100) / 100.0;
    }

    /**
     * Generate the unique ID for given object types
     * 
     * @param objectType Object type (movie/customer/reservation)
     * @return Unique ID for the object type
     */
    public static String generateUniqueId(int objectType) {
        if (objectType == OBJECT_MOVIE) {
            return "M" + UUID.randomUUID();
        } else if (objectType == OBJECT_CUSTOMER) {
            return "C" + UUID.randomUUID();
        } else if (objectType == OBJECT_RESERVATION) {
            return "R" + UUID.randomUUID();
        }
        throw new IllegalArgumentException("Invalid object type " + objectType);
    }

    /**
     * Print theater schedules
     * 
     * @param theater
     */
    public static void printTheaterSchedule(Theater theater) {
        Map<LocalDate, List<Showing>> schedules = theater.getSchedules();
        schedules.entrySet()
                 .stream()
                 .sorted(Map.Entry.comparingByKey())
                 .map(Map.Entry::getValue)
                 .forEach(s -> {
                    LocalDate showDate = s.get(0).getStartTime().toLocalDate();
                    printTheaterSchedule(theater, showDate, s);
                 });
        System.out.println();
        System.out.println();
    }

    /**
     * Generate theater schedule with JSON format
     */
    public static void generateJSONOutput(Theater theater) {
        BookingService bookingService = BookingSystem.getInstance();
        Map<LocalDate, List<Showing>> schedules = theater.getSchedules();
        StringWriter sw = new StringWriter();
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonGeneratorFactory factory = Json.createGeneratorFactory(properties);
        JsonGenerator jsongenerator = factory.createGenerator(sw);
        jsongenerator
            .writeStartObject()
                .write("TheaterName", theater.getName())
                .writeStartArray("Schedules");
        schedules.entrySet()
                 .stream()
                 .sorted(Map.Entry.comparingByKey())
                 .forEach(entry -> {
                    jsongenerator.writeStartObject()
                            .write("Date", entry.getKey().toString())
                            .writeStartArray("Schedule");
                    for (Showing s : entry.getValue()) {
                        double withDiscount = bookingService.calculateCost(s);
                        jsongenerator.writeStartObject()
                                        .write("Sequence", s.getSequenceOfTheDay())
                                        .write("ShowDateTime", s.getStartTime().toString())
                                        .write("Movie", s.getMovie().getTitle())
                                        .write("RunningTime", humanReadableFormat(s.getMovie().getRunningTime()))
                                        .write("TicketPrice", "$" + s.getMovieFee());
                        if (withDiscount < s.getMovieFee()) {
                            jsongenerator.write("Discount", "$" + bookingService.calculateCost(s));
                        }
                        jsongenerator.writeEnd();
                    }
                    jsongenerator.writeEnd()
                            .writeEnd();
                 });
        jsongenerator.writeEnd()
                .writeEnd();
        jsongenerator.close();
        System.out.println(sw.toString());
    }

    /**
     * Print the specific show date theater schedule
     * 
     * @param theater
     * @param showDate
     * @param schedule
     */
    private static void printTheaterSchedule(Theater theater, LocalDate showDate, List<Showing> schedule) {
        BookingService bookingService = BookingSystem.getInstance();
        System.out.print(theater.getName());
        System.out.print("  ");
        System.out.print(showDate);
        System.out.println();
        System.out.println("===================================================");
        schedule.forEach(s -> {
            double withDiscount = bookingService.calculateCost(s);
            String printing = s.getSequenceOfTheDay() + ": " 
                            + s.getStartTime() + " " 
                            + s.getMovie().getTitle() + " " 
                            + humanReadableFormat(s.getMovie().getRunningTime()) 
                            + " $" + s.getMovieFee();
            if (withDiscount < s.getMovieFee()) {
                printing += " (With discount $" + bookingService.calculateCost(s) + ")";
            }
            System.out.println(printing);
        });
        System.out.println("===================================================");
    }

    /**
     * Create human readable date/time format
     * 
     * @param duration
     * @return
     */
    private static String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    /**
     * Handle the plural to add (s) postfix when printing the message
     * 
     * @param value long type value to be evaluated
     * @return empty string or "s" based on the value
     */
    private static String handlePlural(long value) {
        return value == 1 ? "" : "s";
    }

}
