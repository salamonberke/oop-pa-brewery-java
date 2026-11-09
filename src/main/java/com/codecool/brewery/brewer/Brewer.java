package com.codecool.brewery.brewer;

import java.time.DayOfWeek;

/**
 * Represents a brewer working at the brewery.
 * You need to extend this class to support different types of brewers.
 */
public abstract class Brewer {
    private String name;
    private int yearsOfExperience;

    public Brewer(String name, int yearsOfExperience) {}

    // TODO: Add method to check if brewer is available on a specific day
    // TODO: Consider how different types of brewers have different schedules
    // TODO: Consider isAvailableOn may accept parameters for day checks
    public abstract boolean isAvailableOn(DayOfWeek day);

    public String getName() { return this.name; }

    public int getYearsOfExperience() { return this.yearsOfExperience; }
}

/*
Check if a specific brewer is available on a given day
Track brewing experience
Identify the role of each brewer
Master Brewer can brew a specific beer on a given day;
Each day's production is independent - beer can be fully produced in a single day
On each day, the master can produce as many batches (liters) as there are helpers available that day
Example: Master brewer requested to produce beer on Monday and Thursday
Monday: 3 helpers available → 3 batches can be produced on Monday
Thursday: 2 helpers available → 2 batches can be produced on Thursday
Total production capacity for Monday and Thursday: 5 batches (3 + 2)
 */
