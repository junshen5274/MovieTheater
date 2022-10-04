package com.jpmc.theater;

import java.util.Objects;

public class Customer {

    private String name;
    private String customerId;

    /**
     * Customer constructor
     * 
     * @param name customer name
     */
    public Customer(String name) {
        this.customerId = Utility.generateUniqueId(Utility.OBJECT_CUSTOMER);
        this.name = name;
    }

    /**
     * Get the customer unique Id
     * 
     * @return The customer unique Id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Get the customer name
     * 
     * @return The customer name
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) 
            && Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, customerId);
    }

    @Override
    public String toString() {
        return "name: " + name;
    }

}