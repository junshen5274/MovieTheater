package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShowingTests {

    private static Theater theater;
    private static Movie movie;

    @BeforeAll
    static void setup() {
        BookingService bookingService = BookingSystem.getInstance();
        theater = new Theater(BookingService.THEATER_AMC_NY_GARDENCITY);
        movie = bookingService.createMovie(
                "Spider-Man: No Way Home", 
                Duration.ofMinutes(90),
                12.5, 
                Movie.MOVIE_CODE_SPECIAL, 
                "Spider-Man: No Way Home");
    }
    
    @Test
    void whenCreateNewShowing_showingIsValid() {
        Showing showing = BookingSystem.getInstance().createShowing(theater, movie, 
                1, LocalDate.now(), LocalTime.of(7, 30));
        
        assertNotNull(showing);
    }

    @Test
    void whenSequenceOutOfMax_showingIsInvalid() {
        Showing showing = BookingSystem.getInstance().createShowing(theater, movie, 
        25, LocalDate.now(), LocalTime.of(23, 45));

        assertNull(showing);
    }
}
