package com.jpmc.theater;

import java.time.LocalDateTime;
import java.util.Objects;

public class Showing {
    private Theater theater;
    private Movie movie;
    /**
     * The sequence of the day this movie will be shown in the given theater
     */
    private int sequenceOfTheDay;
    /**
     * The movie show date and time
     */
    private LocalDateTime showStartTime;

    /**
     * Showing object constructor
     * 
     * @param theater
     * @param movie
     * @param sequenceOfTheDay
     * @param showStartTime
     */
    public Showing(Theater theater, Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) throws InvalidShowingException {
        if (sequenceOfTheDay > theater.getMaxNumberOfShowing()) {
            throw new InvalidShowingException();
        }
        this.theater = theater;
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Theater getTheater() {
        return theater;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double getMovieFee() {
        return movie.getTicketPrice();
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Showing showing = (Showing) o;
        return Objects.equals(theater.getName(), showing.theater.getName())
                && Objects.equals(movie, showing.movie)
                && sequenceOfTheDay == showing.sequenceOfTheDay
                && Objects.equals(showStartTime, showing.showStartTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theater.getName(), movie, sequenceOfTheDay, showStartTime);
    }
}
