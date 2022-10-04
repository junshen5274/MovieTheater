package com.jpmc.theater;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieTests {

    private static Movie movie;

    @BeforeAll
    static void setup() {
        movie = BookingSystem.getInstance().createMovie(
                "Spider-Man: No Way Home", 
                Duration.ofMinutes(90),
                12.5, 
                Movie.MOVIE_CODE_SPECIAL, 
                "Spider-Man: No Way Home");
    }

    @Test
    void movieDuration_asExpected() {
        //Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        //assertEquals(10, spiderMan.calculateTicketPrice(showing));

        assertEquals(Duration.ofMinutes(90), movie.getRunningTime());
    }

    @Test
    void whenCreateNewMovie_scheduleIsEmpty() {
        assertTrue(movie.getShowing().isEmpty());
    }
}
