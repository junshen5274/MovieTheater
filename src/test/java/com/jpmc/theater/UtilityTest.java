package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UtilityTest {
    
    @Test
    void roundDoubleToTwoPlaces() {
        double price = 12.55;
        double discountPercentage = 15.0 / 100.0;
        double finalPrice = Utility.roundToTwoPlaces(price - price * discountPercentage);
        assertEquals(Double.valueOf(10.67).doubleValue(), finalPrice);
    }

    @Test
    void objectUniqueId() {
        String movieId = Utility.generateUniqueId(Utility.OBJECT_MOVIE);
        assertTrue(movieId.startsWith("M"));

        String invalidCustomerId = Utility.generateUniqueId(Utility.OBJECT_RESERVATION);
        assertFalse(invalidCustomerId.startsWith("C"));
    }
}
