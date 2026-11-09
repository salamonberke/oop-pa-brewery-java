package com.codecool.brewery.brewer;

import java.time.DayOfWeek;

public class MasterBrewer extends Brewer{
    public MasterBrewer(String name, int yearsOfExperience) {
        super(name, yearsOfExperience);
    }

    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        return true;
    }
}
