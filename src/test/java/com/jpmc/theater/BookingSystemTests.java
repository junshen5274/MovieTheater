package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BookingSystemTests {

    private static Map<String, Theater> theaters;
    private static Customer customer1;

    @BeforeAll
    static void setup() {
        BookingService service = BookingSystem.getInstance();
        theaters = service.initializeMovieShowings();

        customer1 = new Customer("Susie Wooden");
    }
    
    @Test
    void bookingSystemSingletonTest () {
        BookingService service1 = BookingSystem.getInstance();
        BookingService service2 = BookingSystem.getInstance();
        assertEquals(service1, service2);
        assertTrue(service1 == service2);
    }

    @Test
    void createMovieTest() {
        Movie movie = BookingSystem.getInstance().createMovie(
                "Spider-Man: No Way Home", 
                Duration.ofMinutes(90),
                12.5, 
                Movie.MOVIE_CODE_SPECIAL, 
                "Spider-Man: No Way Home");
        assertNotNull(movie);
    }

    @Test
    void createShowingTest() {
        BookingService bookingService = BookingSystem.getInstance();
        Theater theater = new Theater(BookingService.THEATER_AMC_NY_GARDENCITY);
        Movie movie = bookingService.createMovie(
                "Spider-Man: No Way Home", 
                Duration.ofMinutes(90),
                12.5, 
                Movie.MOVIE_CODE_SPECIAL, 
                "Spider-Man: No Way Home");
        Showing showing = BookingSystem.getInstance().createShowing(theater, movie, 
                1, LocalDate.now(), LocalTime.of(7, 30));
        assertNotNull(showing);
    }

    @Test
    void afterInitialize_amcNYGardenCityInclude9ShowingsForCurrentDay() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate showDate = LocalDate.now();
        List<Showing> showings = theater.getSchedule(showDate);
        assertTrue(showings.size() == 9);
    }

    @Test
    void afterInitialize_amcNYGardenCityInclude12ShowingsFor7thOfOctober() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate seventhDay = LocalDate.of(2022, 10, 7);
        List<Showing> showings = theater.getSchedule(seventhDay);
        assertTrue(showings.size() == 12);
    }

    @Test
    void afterInitialize_amcNJFreeholdInclude6ShowingsForCurrentDay() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NJ_FREEHOLD);
        LocalDate showDate = LocalDate.now();
        List<Showing> showings = theater.getSchedule(showDate);
        assertTrue(showings.size() == 6);
    }

    @Test
    void afterInitialize_amcNJFreeholdInclude9ShowingsFor7thOfOctober() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NJ_FREEHOLD);
        LocalDate seventhDay = LocalDate.of(2022, 10, 7);
        List<Showing> showings = theater.getSchedule(seventhDay);
        assertTrue(showings.size() == 9);
    }

    @Test
    void reserveShowingSuccess() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate showDate = LocalDate.now();
        LocalTime showTime = LocalTime.of(9, 0);
        try {
            Reservation reservation = BookingSystem.getInstance().reserve(
                theater, LocalDateTime.of(showDate, showTime), customer1, 1, 1);

            assertNotNull(reservation.getReservationId());
            assertEquals(theater, reservation.getTheater());
            assertTrue(theater.getSchedule(showDate).contains(reservation.getShowing()));
        } catch(InvalidReserveException ire) {
            // Won't coming here
        }
    }

    @Test
    void reservationFailure() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate showDate = LocalDate.now();
        LocalTime showTime = LocalTime.of(10, 0);
        assertThrows(InvalidReserveException.class, () -> BookingSystem.getInstance().reserve(
            theater, LocalDateTime.of(showDate, showTime), customer1, 2, 1));
    }

    @Test
    void reservationCostTest() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NJ_FREEHOLD);
        LocalDate showDate = LocalDate.now();
        LocalTime showTime = LocalTime.of(13, 0);
        try {
            Reservation reservation = BookingSystem.getInstance().reserve(
                theater, LocalDateTime.of(showDate, showTime), customer1, 3, 4);

            assertEquals(33.0, reservation.getCost());
        } catch(InvalidReserveException ire) {
            // Won't coming here
        }
    }

    @Test
    void whenSpecialMovie_afterApplyDiscount_costIs10dollar() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate showDate = LocalDate.now();
        LocalTime showTime = LocalTime.of(21, 10);
        Showing showing = theater.getSchedule("Spider-Man: No Way Home", 
                LocalDateTime.of(showDate, showTime));
        double showingCost = BookingSystem.getInstance().calculateCost(showing);
        assertEquals(10.0, showingCost);
    }

    @Test
    void firstSequenceMovie_afterApplyDiscount_costIs10dollar() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate showDate = LocalDate.now();
        LocalTime showTime = LocalTime.of(9, 0);
        Showing showing = theater.getSchedule("Turning Red", 
                LocalDateTime.of(showDate, showTime));
        double showingCost = BookingSystem.getInstance().calculateCost(showing);
        assertEquals(8.0, showingCost);
    }

    @Test
    void secondSequenceMovie_afterApplyDiscount_costIs10dollar() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NJ_FREEHOLD);
        LocalDate showDate = LocalDate.now();
        LocalTime showTime = LocalTime.of(10, 0);
        Showing showing = theater.getSchedule("Turning Red", 
                LocalDateTime.of(showDate, showTime));
        double showingCost = BookingSystem.getInstance().calculateCost(showing);
        assertEquals(9.0, showingCost);
    }

    @Test
    void timeBetween11and4_afterApplyDiscount_costIs8dollarQuaterCents() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate showDate = LocalDate.now();
        LocalTime showTime = LocalTime.of(14, 30);
        Showing showing = theater.getSchedule("Turning Red", 
                LocalDateTime.of(showDate, showTime));
        double showingCost = BookingSystem.getInstance().calculateCost(showing);
        assertEquals(8.25, showingCost);
    }

    @Test
    void on7thDay_afterApplyDiscount_costIs8dollar() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate seventhDay = LocalDate.of(2022, 10, 7);
        LocalTime showTime = LocalTime.of(21, 0);
        Showing showing = theater.getSchedule("The Batman", 
                LocalDateTime.of(seventhDay, showTime));
        double showingCost = BookingSystem.getInstance().calculateCost(showing);
        assertEquals(8.0, showingCost);
    }

    @Test
    void multipleRulesTest1_afterApplyDiscount_costIs9dollar37Cents() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        LocalDate showDate = LocalDate.now();
        LocalTime showTime = LocalTime.of(11, 0);
        Showing showing = theater.getSchedule("Spider-Man: No Way Home", 
                LocalDateTime.of(showDate, showTime));
        double showingCost = BookingSystem.getInstance().calculateCost(showing);
        // Rule 1 - special code 20% ($2.5), Rule 2 - 2nd showing ($2.0), Rule 3 - show time between 11~16 ($3.13)
        assertEquals(9.37, showingCost);
    }

    @Test
    void multipleRulesTest2_afterApplyDiscount_costIs8dollar() {
        Theater theater = theaters.get(BookingService.THEATER_AMC_NJ_FREEHOLD);
        LocalDate seventhDay = LocalDate.of(2022, 10, 7);
        LocalTime showTime = LocalTime.of(9, 0);
        Showing showing = theater.getSchedule("Turning Red", 
                LocalDateTime.of(seventhDay, showTime));
        double showingCost = BookingSystem.getInstance().calculateCost(showing);
        // Rule 1 - 1st showing ($3.0), Rule 2 - show day on 7th ($1.0)
        assertEquals(8.0, showingCost);
    }

    @Test
    void printTheaterScheduleTest() {
        Theater theater1 = theaters.get(BookingService.THEATER_AMC_NJ_FREEHOLD);
        BookingSystem.getInstance().printTheaterSchedule(theater1);

        Theater theater2 = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        BookingSystem.getInstance().printTheaterSchedule(theater2);
    }

    @Test
    void generateJSONOutputTest() {
        Theater theater1 = theaters.get(BookingService.THEATER_AMC_NJ_FREEHOLD);
        BookingSystem.getInstance().generateJSONOutput(theater1);

        Theater theater2 = theaters.get(BookingService.THEATER_AMC_NY_GARDENCITY);
        BookingSystem.getInstance().generateJSONOutput(theater2);
    }
}
