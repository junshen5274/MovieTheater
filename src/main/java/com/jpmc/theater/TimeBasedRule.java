package com.jpmc.theater;

import java.time.LocalDateTime;

/**
 * Movie showing time discount rules:
 * Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount
 * Any movies showing on 7th, you'll get 1$ discount
 */
public class TimeBasedRule implements DiscountRule {

    /**
     * Time discount percentage value - showing start between 11AM and 4PM
     */
    public static final double DISCOUNT_TIME_11_16 = 25.0 / 100.0;
    /**
     * Time discount constant value - 7th of the month
     */
    public static final double DISCOUNT_DAY_7TH = 1.0;
    private Showing showing;

    public TimeBasedRule(Showing showing) {
        this.showing = showing;
    }

    @Override
    public double getDiscount() {
        LocalDateTime startTime = showing.getStartTime();

        // calculate the discount if the showing starts between 11AM ~ 4PM
        double hourDiscount = 0.0;
        int hour = startTime.getHour();
        if (hour >=11 && hour <= 16) {
            hourDiscount = Utility.roundToTwoPlaces(showing.getMovie().getTicketPrice() * DISCOUNT_TIME_11_16);
        }
        
        // calculate the discount if the showing is on 7th of the month
        double dateDiscount = 0.0;
        if (startTime.getDayOfMonth() == 7) {
            dateDiscount = DISCOUNT_DAY_7TH;
        }

        return hourDiscount > dateDiscount ? hourDiscount : dateDiscount;
    }
    
}
