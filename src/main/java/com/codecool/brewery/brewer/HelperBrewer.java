package com.codecool.brewery.brewer;

import java.time.DayOfWeek;
import java.util.List;

public class HelperBrewer extends Brewer{
    private final List<DayOfWeek> availableDays;

    public HelperBrewer(String name, int yearsOfExperience, List<DayOfWeek> availableDays) {
        super(name, yearsOfExperience);

        if (availableDays == null) {
            throw new IllegalArgumentException("Available days cannot be null");
        }
        this.availableDays = availableDays;
    }

    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        for (DayOfWeek availableDay : availableDays) {
            if (availableDay.equals(day)) {
                return true;
            }
        }
        return false;
    }
}