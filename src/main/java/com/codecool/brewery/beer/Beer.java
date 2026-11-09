package com.codecool.brewery.beer;

import java.time.LocalDate;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
public abstract class Beer {
    private final String name;
    private final double alcoholContent;
    private final LocalDate productionDate;

    public Beer(String name, double alcoholContent, LocalDate productionDate) {
        this.name = name;
        this.productionDate = productionDate;

        if (alcoholContent < 0 || alcoholContent > 100) {
            throw new IllegalArgumentException("Invalid alcohol content");
        }
        this.alcoholContent = alcoholContent;
    }

    // TODO: Add method to calculate days until beer is ready for sale
    // TODO: Consider how to handle different fermentation times for different beer types
    public abstract int getDaysUntilReady();

    // TODO: Add method to get storage temperature requirement
    // TODO: Consider canBeStoredAt method may accept parameters for temperature checks
    public abstract boolean canBeStoredAt(int temperature);

    // TODO: Add method to get fermentation days
    public abstract int getFermentationDays();

    public LocalDate getProductionDate() { return this.productionDate; }

    public double getAlcoholContent() { return this.alcoholContent; }

    public String getName() { return this.name; }
}