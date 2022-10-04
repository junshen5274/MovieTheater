package com.jpmc.theater;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie {
    /**
     * Movie default code (None), there is no any discount for this type of movies.
     */
    public static int MOVIE_CODE_NONE = 0;
    /**
     * Special movie code. Refer to {@code}SpecialRule.java{@code} for the discount logic.
     */
    public static int MOVIE_CODE_SPECIAL = 1;

    private String movieId;
    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;
    private List<Showing> showings;

    /**
     * Movie object constructor without special code
     * 
     * @param title The title of a movie
     * @param runningTime The running time of the movie
     * @param ticketPrice The ticket raw price
     * @param description The description of the movie
     */
    public Movie(String title, Duration runningTime, double ticketPrice, String description) {
        this(title, runningTime, ticketPrice, MOVIE_CODE_NONE, description);
    }

    /**
     * Movie object constructor with special code
     * 
     * @param title The title of a movie
     * @param runningTime The running time of the movie
     * @param ticketPrice The ticket raw price
     * @param specialCode The special movie code
     * @param description The description of the movie
     */
    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode, String description) {
        movieId = Utility.generateUniqueId(Utility.OBJECT_MOVIE);
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
        this.description = description;
        init();
    }

    /**
     * Initialize the movie showing list
     */
    public void init() {
        showings = new ArrayList<>();
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getSpeicalCode() {
        return specialCode;
    }

    public boolean addShowing(Showing showing) {
        if (!showings.contains(showing)) {
            return showings.add(showing);
        }
        return false;
    }

    public List<Showing> getShowing() {
        return showings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode)
                && Objects.equals(movieId, movie.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, title, description, runningTime, ticketPrice, specialCode);
    }
}