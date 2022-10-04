package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facade class for Booking System
 */
public class BookingSystem implements BookingService {
    
    private BookingSystem() {}

    private static class SingletonHelper {
        private static final BookingSystem INSTANCE = new BookingSystem();
    }

    /**
     * Singleton pattern to return singleton BookingSystem instance
     * 
     * @return A singleton instance of BookingSystem
     */
    public static BookingSystem getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public double calculateCost(Showing showing) {
        return Utility.roundToTwoPlaces(
            showing.getMovie().getTicketPrice() - Utility.roundToTwoPlaces(DiscountRuleManager.getDiscount(showing)));
    }

    @Override
    public Reservation reserve(Theater theater, LocalDateTime showDateTime, Customer customer, 
                               int sequence, int howManyTickets) 
        throws InvalidReserveException {
        
        List<Showing> schedule = theater.getSchedule(showDateTime.toLocalDate());
        if (schedule.isEmpty() || sequence > schedule.size()) {
            throw new InvalidReserveException("There is no schedule available to reserve by the given sequence " 
                + sequence + " in Theater " + theater.getName() + " (The sequence is out of limitation).");
        }

        Showing showing = null;
        for (Showing s : schedule) {
            if (s.getSequenceOfTheDay() == sequence && s.getStartTime().equals(showDateTime))
                showing = s;
        }
        if (showing == null) {
            throw new InvalidReserveException("There is no schedule available to reserve by the given sequence " 
                + sequence + " in Theater " + theater.getName() + " (The sequence and showDateTime doesn't exist).");
        }
        
        double showingCost = calculateCost(showing) * howManyTickets;
        return new Reservation(theater, customer, showing, howManyTickets, showingCost);
    }

    @Override
    public Movie createMovie(String title, Duration duration, double price, int specialCode, String description) {
        return new Movie(title, duration, price, specialCode, description);
    }

    @Override
    public Showing createShowing(Theater theater, Movie movie, int sequenceOfDay, 
                                 LocalDate showDate, LocalTime showTime) {
        Showing showing;
        try {
            showing = new Showing(theater, movie, sequenceOfDay, 
                                  LocalDateTime.of(showDate, showTime));
        } catch (InvalidShowingException ise) {
            System.out.println("Invalid movie showing scheduling - the sequence of the day exceeds the max number of the theater.");
            return null;
        }
        return showing;
    }

    @Override
    public Map<String, Theater> initializeMovieShowings() {
        Map<String, Theater> theaters = new HashMap<>();
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        LocalDate seventhDay;
        if (day < 7) {
            seventhDay = today.plusDays(7 - day);
        } else {
            int month, year;
            if (today.getMonthValue() < 12) {
                month = today.getMonthValue() + 1;
                year = today.getYear();
            } else {
                month = 1;
                year = today.getYear() + 1;
            }
            seventhDay = LocalDate.of(year, month, 7);
        }
        
        // Initialize Theaters
        Theater amcNYGardenCity = new Theater(THEATER_AMC_NY_GARDENCITY);
        Theater amcNJFreehold = new Theater(THEATER_AMC_NJ_FREEHOLD);
        theaters.put(THEATER_AMC_NY_GARDENCITY, amcNYGardenCity);
        theaters.put(THEATER_AMC_NJ_FREEHOLD, amcNJFreehold);

        // Initialize showings
        initializeMovieShowings(amcNYGardenCity, today);
        initializeMovieShowings(amcNJFreehold, today);
        initializeMovieShowings(amcNYGardenCity, seventhDay);
        initializeMovieShowings(amcNJFreehold, seventhDay);

        return theaters;
    }

    @Override
    public void initializeMovieShowings(Theater theater, LocalDate showDate) {
        if (showDate == null) {
            showDate = LocalDate.now();
        }

        // Initialize movies
        Movie spiderMan = createMovie(
            "Spider-Man: No Way Home", 
            Duration.ofMinutes(90), 
            12.5, 
            Movie.MOVIE_CODE_SPECIAL,
            "Spider-Man: No Way Home");
        Movie turningRed = createMovie(
            "Turning Red", 
            Duration.ofMinutes(85), 
            11,
            Movie.MOVIE_CODE_NONE,
            "Turning Red");
        Movie theBatMan = createMovie(
            "The Batman", 
            Duration.ofMinutes(95), 
            9,
            Movie.MOVIE_CODE_NONE,
            "The Batman");
        
        // Initialize schedule (showings) for all movies for AMC NY Garden City
        if (THEATER_AMC_NY_GARDENCITY.equals(theater.getName())) {
            if (showDate.getDayOfMonth() == 7) { // speicial date
                scheduleShowing(theater, spiderMan, 1, showDate, LocalTime.of(7, 30));
                scheduleShowing(theater, turningRed, 2, showDate, LocalTime.of(9, 0));
                scheduleShowing(theater, theBatMan, 3, showDate, LocalTime.of(10, 25));
                scheduleShowing(theater, spiderMan, 4, showDate, LocalTime.of(11, 0));
                scheduleShowing(theater, turningRed, 5, showDate, LocalTime.of(11, 30));
                scheduleShowing(theater, theBatMan, 6, showDate, LocalTime.of(12, 50));
                scheduleShowing(theater, spiderMan, 7, showDate, LocalTime.of(13, 30));
                scheduleShowing(theater, turningRed, 8, showDate, LocalTime.of(14, 0));
                scheduleShowing(theater, theBatMan, 9, showDate, LocalTime.of(15, 20));
                scheduleShowing(theater, spiderMan, 10, showDate, LocalTime.of(16, 10));
                scheduleShowing(theater, turningRed, 11, showDate, LocalTime.of(18, 45));
                scheduleShowing(theater, theBatMan, 12, showDate, LocalTime.of(21, 0));
            } else { // default schedule
                scheduleShowing(theater, turningRed, 1, showDate, LocalTime.of(9, 0));
                scheduleShowing(theater, spiderMan, 2, showDate, LocalTime.of(11, 0));
                scheduleShowing(theater, theBatMan, 3, showDate, LocalTime.of(12, 50));
                scheduleShowing(theater, turningRed, 4, showDate, LocalTime.of(14, 30));
                scheduleShowing(theater, spiderMan, 5, showDate, LocalTime.of(16, 10));
                scheduleShowing(theater, theBatMan, 6, showDate, LocalTime.of(17, 50));
                scheduleShowing(theater, turningRed, 7, showDate, LocalTime.of(19, 30));
                scheduleShowing(theater, spiderMan, 8, showDate, LocalTime.of(21, 10));
                scheduleShowing(theater, theBatMan, 9, showDate, LocalTime.of(23, 0));
            }
        }
        
        // Initialize schedule (showings) for all movies for AMC NJ Freehold
        if (THEATER_AMC_NJ_FREEHOLD.equals(theater.getName())) {
            if (showDate.getDayOfMonth() == 7) { // special date
                scheduleShowing(theater, turningRed, 1, showDate, LocalTime.of(9, 0));
                scheduleShowing(theater, spiderMan, 2, showDate, LocalTime.of(11, 0));
                scheduleShowing(theater, theBatMan, 3, showDate, LocalTime.of(12, 50));
                scheduleShowing(theater, turningRed, 4, showDate, LocalTime.of(14, 30));
                scheduleShowing(theater, spiderMan, 5, showDate, LocalTime.of(16, 10));
                scheduleShowing(theater, theBatMan, 6, showDate, LocalTime.of(17, 50));
                scheduleShowing(theater, turningRed, 7, showDate, LocalTime.of(19, 30));
                scheduleShowing(theater, spiderMan, 8, showDate, LocalTime.of(21, 10));
                scheduleShowing(theater, theBatMan, 9, showDate, LocalTime.of(23, 0));
            } else { // default schedule
                scheduleShowing(theater, spiderMan, 1, showDate, LocalTime.of(7, 30));
                scheduleShowing(theater, turningRed, 2, showDate, LocalTime.of(10, 0));
                scheduleShowing(theater, turningRed, 3, showDate, LocalTime.of(13, 0));
                scheduleShowing(theater, spiderMan, 4, showDate, LocalTime.of(15, 30));
                scheduleShowing(theater, turningRed, 5, showDate, LocalTime.of(19, 0));
                scheduleShowing(theater, spiderMan, 6, showDate, LocalTime.of(22, 40));
            }
        }
    }

    @Override
    public void printTheaterSchedule(Theater theater) {
        Utility.printTheaterSchedule(theater);
    }

    @Override
    public void generateJSONOutput(Theater theater) {
        Utility.generateJSONOutput(theater);
    }

    /**
     * Crate movie schedule (showing)
     * 
     * @param theater
     * @param movie
     * @param sequenceOfDay
     * @param showDate
     * @param showTime
     */
    private void scheduleShowing(Theater theater, Movie movie, int sequenceOfDay, 
                                 LocalDate showDate, LocalTime showTime) {
        Showing showing = createShowing(theater, movie, sequenceOfDay, showDate, showTime);
        movie.addShowing(showing);
        theater.addSchedule(showDate, showing);
    }
}
