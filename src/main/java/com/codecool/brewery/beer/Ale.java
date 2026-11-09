package com.codecool.brewery.beer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Ale extends Beer{
    Ale(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getDaysUntilReady() {
        return (int) ChronoUnit.DAYS.between(productionDate, LocalDate.now());
    }

    @Override
    public boolean canBeStoredAt(double temperature) {
        return temperature >= 15 && temperature <= 25;
    }

    @Override
    public int getFermentationDays() {
        return 14;
    }
}
