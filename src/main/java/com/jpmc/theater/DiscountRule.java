package com.jpmc.theater;

/**
 * Interface for the discount rules
 */
public interface DiscountRule {
    
    /**
     * Caclulate the discount for the showing
     * 
     * @return the discount price of the movie ticket 
     */
    public double getDiscount();

}
