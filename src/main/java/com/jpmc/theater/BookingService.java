package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public interface BookingService {

    public static final String THEATER_AMC_NY_GARDENCITY = "AMC NY Garden City";
    public static final String THEATER_AMC_NJ_FREEHOLD = "AMC NJ Freehold";

    /**
     * Calculate the cost of a showing considering all of discount rules
     * 
     * @param showing The showtime of a movie
     * @return The calculated cost for a particular movie showing for only one ticket
     */
    public double calculateCost(Showing showing);

    /**
     * Reserve a moving from the given theater for the customer
     * 
     * @param theater The theater where the movie will be showing 
     * @param showDateTime The date time of the showing
     * @param customer The customer who reserves the movie
     * @param sequence The sequence of movie showing of the day
     * @param howManyTickets The total number of tickets the customer is going to reserve
     * @return Instance of Reservation
     * @throws InvalidReserveException
     */
    public Reservation reserve(Theater theater, LocalDateTime showDateTime, Customer customer, int sequence, int howManyTickets) 
        throws InvalidReserveException;
    
    /**
     * Create the movie object
     * 
     * @param title The title of the movie
     * @param duration The duration of the movie
     * @param price The price of the movie
     * @param specialCode The special movie code of the movie
     * @param description The description of the movie
     * @return
     */
    public Movie createMovie(String title, Duration duration, double price, int specialCode, String description);
    
    /**
     * Create the movie showing object
     * 
     * @param theater The theater the movie will be shown
     * @param movie The movie for this showing
     * @param sequenceOfDay The movie showing sequence of the day in the theater  
     * @param showDate The movie show date 
     * @param showTime The movie show time
     * @return The showing object, or return null if the sequenceOfDay exceeds the theater's max showing number 
     */
    public Showing createShowing(Theater theater, Movie movie, int sequenceOfDay, 
                                 LocalDate showDate, LocalTime showTime);
    
    /**
     * Initialize the movie showings for all theaters
     * 
     * @return A map with key is the theater name and value is the theater object
     */
    public Map<String, Theater> initializeMovieShowings();

    /**
     * Initialize the theater's movie showings
     * 
     * @param theater The theater where the movie will be shown
     * @param showDate The date of the showings
     */
    public void initializeMovieShowings(Theater theater, LocalDate showDate);

    /**
     * Print the theater schedule
     * 
     * @param theater
     */
    public void printTheaterSchedule(Theater theater);

    /**
     * Generate theater schedule with JSON format
     * 
     * @param theater
     */
    public void generateJSONOutput(Theater theater);
}
