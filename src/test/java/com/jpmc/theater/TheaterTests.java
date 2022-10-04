package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

public class TheaterTests {
    @Test
    void newTheater_hasEmptySchedule() {
        Theater theater = new Theater(BookingService.THEATER_AMC_NY_GARDENCITY);
        assertTrue(theater.getSchedule(LocalDate.now()).isEmpty());
    }
}
