package com.codecool.brewery.beer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Ale extends Beer{
    public Ale(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getDaysUntilReady() {
        return (int) ChronoUnit.DAYS.between(getProductionDate(), LocalDate.now());
    }

    @Override
    public boolean canBeStoredAt() {
        return getAlcoholContent() >= 15 && getAlcoholContent() <= 25;
    }

    @Override
    public int getFermentationDays() {
        return 14;
    }
}
