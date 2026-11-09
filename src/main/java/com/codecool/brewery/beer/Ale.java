package com.codecool.brewery.beer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Ale extends Beer{
    public Ale(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getDaysUntilReady() {
        return Math.max(0, getFermentationDays() - (int) ChronoUnit.DAYS.between(getProductionDate(), LocalDate.now()));
    }

    @Override
    public boolean canBeStoredAt(int temperature) {
        return temperature >= 15 && temperature <= 25;
    }

    @Override
    public int getFermentationDays() {
        return 14;
    }
}
