package com.jpmc.theater;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Theater {

    private static final int MAX_NUMBER_OF_SHOWING = 20;
    private String name;
    private Map<LocalDate, List<Showing>> schedules;

    public Theater(String name) {
        this.name = name;
        schedules = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getMaxNumberOfShowing() {
        return MAX_NUMBER_OF_SHOWING;
    }

    /**
     * Get list of showing schedule of the give show date
     * 
     * @param showDate
     * @return
     */
    public List<Showing> getSchedule(LocalDate showDate) {
        if (schedules.containsKey(showDate)) {
            return schedules.get(showDate);
        }
        return Collections.emptyList();
    }

    /**
     * Get a showing by given movie name and show date time
     * 
     * @param movieName
     * @param showDateTime
     * @return
     */
    public Showing getSchedule(String movieName, LocalDateTime showDateTime) {
        LocalDate showDate = showDateTime.toLocalDate();
        List<Showing> showings = getSchedule(showDate);
        for (Showing s : showings) {
            if (s.getMovie().getTitle().equals(movieName) && s.getStartTime().equals(showDateTime)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Get all schedules of the theater
     * 
     * @return
     */
    public Map<LocalDate, List<Showing>> getSchedules() {
        return schedules;
    }

    /**
     * Add showing schedule to the theater
     * 
     * @param showDate
     * @param showing
     * @return true/false to indicate if adding the shedule is successful
     */
    public boolean addSchedule(LocalDate showDate, Showing showing) {
        if (!schedules.containsKey(showDate)) {
            schedules.put(showDate, new ArrayList<>());
        }
        if (!schedules.get(showDate).contains(showing)) {
            return schedules.get(showDate).add(showing);
        }
        return false;
    }
}
