package com.jpmc.theater;

/**
 * Special discount rule:
 * 20% discount for the special movie
 */
public class SpecialRule implements DiscountRule {

    /**
     * Special discount percentage
     */
    public static final double DISCOUNT = 20.0 / 100.0;

    private Showing showing;

    public SpecialRule(Showing showing) {
        this.showing = showing;
    }

    @Override
    public double getDiscount() {
        Movie movie = showing.getMovie();
        if (movie.getSpeicalCode() == Movie.MOVIE_CODE_SPECIAL) {
            return Utility.roundToTwoPlaces(movie.getTicketPrice() * DISCOUNT);
        }
        return 0.0;
    }
    
}
