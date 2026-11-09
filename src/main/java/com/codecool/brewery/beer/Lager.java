package com.codecool.brewery.beer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Lager extends Beer{
    Lager(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getDaysUntilReady() {
        return (int) ChronoUnit.DAYS.between(productionDate, LocalDate.now());
    }

    @Override
    public boolean canBeStoredAt(double temperature) {
        return temperature < 5;
    }

    @Override
    public int getFermentationDays() {
        return 21;
    }
}
