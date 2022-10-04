package com.jpmc.theater;

import java.util.stream.DoubleStream;

/**
 * Discount Rule Manager to calculate the discount for the movie showing
 */
public class DiscountRuleManager {
    
    /**
     * Calculate the movie showing discount based on all rules.
     * Apply the biggest discount rule if multiple rules are applicable.
     * 
     * @param showing The movie showing
     * @return The discount the customer can get for the showing
     */
    public static double getDiscount(Showing showing) {
        DiscountRule specialRule = new SpecialRule(showing);
        double specialDiscount = specialRule.getDiscount();
        
        DiscountRule sequenceRule = new SequenceBasedRule(showing);
        double sequenceDiscount = sequenceRule.getDiscount();

        DiscountRule timeDiscountRule = new TimeBasedRule(showing);
        double timeDiscount = timeDiscountRule.getDiscount();

        return DoubleStream.of(specialDiscount, sequenceDiscount, timeDiscount)
                           .max()
                           .getAsDouble();
    }
}
