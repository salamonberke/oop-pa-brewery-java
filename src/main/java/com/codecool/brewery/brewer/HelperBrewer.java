package com.codecool.brewery.brewer;

import java.time.DayOfWeek;

public class HelperBrewer extends Brewer{
    HelperBrewer(String name, int yearsOfExperience) {
        super(name, yearsOfExperience);
    }

    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        return day == DayOfWeek.MONDAY || day == DayOfWeek.TUESDAY || day == DayOfWeek.WEDNESDAY;
    }
}
