package com.jpmc.theater;

/**
 * Movie showing sequence discount rules:
 * $3 discount for the movie showing 1st of the day
 * $2 discount for the movie showing 2nd of the day
 */
public class SequenceBasedRule implements DiscountRule {

    /**
     * Sequence discount constant value - 1st of the day
     */
    public static final double DISCOUNT_SEQUENCE_1 = 3.0;
    /**
     * Sequence discount constant value - 2nd of the day
     */
    public static final double DISCOUNT_SEQUENCE_2 = 2.0;

    private Showing showing;

    public SequenceBasedRule(Showing showing) {
        this.showing = showing;
    }

    @Override
    public double getDiscount() {
        int showSequence = showing.getSequenceOfTheDay();
        if (showSequence == 1) {
            return DISCOUNT_SEQUENCE_1;
        } else if (showSequence == 2) {
            return DISCOUNT_SEQUENCE_2;
        }
        return 0.0;
    }
    
}
