package com.jpmc.theater;

/**
 * Represents the customer booking result
 */
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Showing showing;
    private int audienceCount;
    private Theater theater;
    private double cost;

    public Reservation(Theater theater, Customer customer, Showing showing, 
                       int audienceCount, double showingCost) {
        reservationId = Utility.generateUniqueId(Utility.OBJECT_RESERVATION);
        this.theater = theater;
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
        this.cost = showingCost;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Theater getTheater() {
        return theater;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Showing getShowing() {
        return showing;
    }

    public Movie getMovie() {
        return showing.getMovie();
    }

    public int getAudienceCount() {
        return audienceCount;
    }

    public double getCost() {
        return cost;
    }

}