package com.codecool.brewery;
import com.codecool.brewery.beer.Ale;
import com.codecool.brewery.beer.Beer;
import com.codecool.brewery.beer.Lager;
import com.codecool.brewery.brewer.Brewer;
import com.codecool.brewery.brewer.HelperBrewer;
import com.codecool.brewery.brewer.MasterBrewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BreweryTest {

    private Brewery brewery;
    private Brewer masterBrewer;
    private Brewer helper1;
    private Brewer helper2;
    private Brewer helper3;

    @BeforeEach
    void setUp() {
        brewery = new Brewery("Test Brewery");
        brewery.addBrewer(new MasterBrewer("Master John", 15));
        brewery.addBrewer(new HelperBrewer("Helper Alice", 3, List.of( DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY)));
        brewery.addBrewer(new HelperBrewer("Helper Bob", 2, List.of( DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)));
        brewery.addBrewer(new HelperBrewer("Helper Charlie", 4, List.of( DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY)));
    }

    @Test
    @DisplayName("Simple Test: Production capacity on single day equals available helpers")
    void testProductionCapacityOnSingleDay() {
        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY));
        assertEquals(2, capacity, "Production capacity should equal number of helpers on Monday");
    }

    @Test
    @DisplayName("Production capacity on multiple days returns sum of helpers")
    void testProductionCapacityOnMultipleDays() {
        int capacity = brewery.calculateProductionCapacity(List.of( DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));
        assertEquals(6, capacity);
    }

    @Test
    @DisplayName("Parsing invalid day name throws exception")
    void testInvalidDayName() {
        assertThrows(IllegalArgumentException.class, () -> {
            brewery.findOptimalProductionDays("TUEASDAY", "FRIDAY");
        });
    }

    @Test
    @DisplayName("Find expired beers returns beers older than 180 days")
    void testFindExpiredBeers() {
        Lager fresh = new Lager("Fresh", 5, LocalDate.now().minusDays(100));
        Lager oldLager = new Lager("Old Lager", 5.5, LocalDate.now().minusDays(190));
        Ale oldAle = new Ale("Old Ale", 6, LocalDate.now().minusDays(200));

        brewery.addBeerToStock(fresh, 1);
        brewery.addBeerToStock(oldLager, 1);
        brewery.addBeerToStock(oldAle, 1);

        List<String> expired = brewery.findMaturedBeers();

        assertEquals(2, expired.size());
        assertTrue(expired.contains("Old Ale"));
        assertTrue(expired.contains("Old Lager"));
        assertFalse(expired.contains("Fresh"));
    }

    @Test
    @DisplayName("Find optimal production days returns days with most helpers")
    void testFindOptimalProductionDays() {
        List<String> optimalDays = brewery.findOptimalProductionDays("MONDAY", "SUNDAY");

        assertEquals(4, optimalDays.size());
        assertTrue(optimalDays.contains("MONDAY"));
        assertTrue(optimalDays.contains("TUESDAY"));
        assertTrue(optimalDays.contains("WEDNESDAY"));
        assertTrue(optimalDays.contains("THURSDAY"));
    }

    @Test
    @DisplayName("Beer type has correct fermentation days")
    void testBeerFermentationDays() {
        Lager lager = new Lager("Test Lager", 5, LocalDate.of(2026, 10, 1));
        Ale ale = new Ale("Test Ale", 5.5, LocalDate.of(2026, 10, 1));

        int lagerFermentation = lager.getFermentationDays();
        int aleFermentation = ale.getFermentationDays();

        assertEquals(21, lagerFermentation);
        assertEquals(14, aleFermentation);
    }

    @Test
    @DisplayName("Beer type has correct storage temperature")
    void testBeerStorageTemperature() {
        Lager lager = new Lager("Test Lager", 5, LocalDate.now());
        Ale ale = new Ale("Test Ale", 5.5, LocalDate.now());

        boolean validLagerTemperature = lager.canBeStoredAt(4);
        boolean invalidLagerTemperature = lager.canBeStoredAt(6);

        boolean validAleTemperature = ale.canBeStoredAt(20);
        boolean invalidAleTemperature = ale.canBeStoredAt(10);

        assertTrue(validLagerTemperature);
        assertFalse(invalidLagerTemperature);

        assertTrue(validAleTemperature);
        assertFalse(invalidAleTemperature);
    }

    @Test
    @DisplayName("Helper brewer availability is correct")
    void testHelperBrewerAvailability() {
        HelperBrewer testHelper = new HelperBrewer("Test helper", 0, List.of( DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
        assertTrue(testHelper.isAvailableOn(DayOfWeek.MONDAY));
        assertFalse(testHelper.isAvailableOn(DayOfWeek.FRIDAY));
    }
}
