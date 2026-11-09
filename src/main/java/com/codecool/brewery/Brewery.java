package com.codecool.brewery;

import com.codecool.brewery.beer.Beer;
import com.codecool.brewery.brewer.Brewer;
import com.codecool.brewery.brewer.HelperBrewer;
import com.codecool.brewery.brewer.MasterBrewer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a brewery that manages beer production and staff.
 * Many methods are incomplete and need to be implemented.
 */
public class Brewery {
    private final String name;
    private final List<Brewer> brewers;
    private final Map<Beer, Integer> stock;

    public Brewery(String name) {
        this.name = name;
        brewers = new ArrayList<>();
        stock = new HashMap<>();
    }

    public void addBrewer(Brewer brewer) {
        if (brewer == null) {
            throw new IllegalArgumentException("Brewer cannot be null");
        }

        if (getMasterBrewer() != null && brewer instanceof MasterBrewer) {
            throw new IllegalArgumentException("Only one Master Brewer is allowed");
        }
        brewers.add(brewer);
    }

    public void addBeerToStock(Beer beer, int liters) {
        if (beer == null) {
            throw new IllegalArgumentException("Beer cannot be null");
        }

        if (liters <= 0) {
            throw new IllegalArgumentException("Liters has to be more than 0");
        }

        if (stock.containsKey(beer)) {
            stock.put(beer, stock.get(beer) + liters);
        } else {
            stock.put(beer, liters);
        }
    }

    /**
     * Retrieves a specific quantity of beer from stock.
     * TODO: Implement this method with proper error handling
     * - What happens if the beer doesn't exist?
     * - What happens if there's not enough quantity?
     * - Should this modify the stock?
     */
    public void retrieveBeerFromStock(Beer beer, int liters) {
        if (beer == null) {
            throw new IllegalArgumentException("Beer cannot be null");
        }

        if (liters <= 0) {
            throw new IllegalArgumentException("Liters has to be more than 0");
        }

        if (!stock.containsKey(beer)) {
            throw new IllegalArgumentException("Beer is not in stock");
        }

        if (stock.get(beer) < liters) {
            throw new IllegalArgumentException("Not enough beer available");
        }

        stock.put(beer, stock.get(beer) - liters);

        // TODO: Implement
    }

    /**
     * Calculates the total production capacity across specific days.
     * Each day is independent - beer can be produced in a single day.
     * TODO: Implement this method
     * - Find the master brewer
     * - Count available helpers on each specified day
     * - Return the sum of helper counts across all days
     */
    public int calculateProductionCapacity(List<DayOfWeek> days) {
        MasterBrewer masterBrewer = getMasterBrewer();
        int numOfHelpers = 0;

        for (DayOfWeek day : days) {
            if (masterBrewer != null && masterBrewer.isAvailableOn(day)) {
                numOfHelpers += countAvailableHelpers(day);
            }
        }
        return numOfHelpers;
    }

    /**
     * Finds beers that are past their prime (older than 180 days).
     * TODO: Implement this method using Stream API
     * - Filter beers by age
     * - Return collection of beer names
     */
    public List<String> findMaturedBeers() {
        List<Beer> matureBeers = stock.keySet().stream()
                .filter(beer -> ChronoUnit.DAYS.between(beer.getProductionDate(), LocalDate.now()) >= 180)
                .toList();

        return matureBeers.stream()
                .map(Beer::getName)
                .toList();
    }

    /**
     * Finds the optimal days for production within a given range.
     * Returns days with maximum helper availability.
     */
    public List<String> findOptimalProductionDays(String startDayName, String endDayName) {
        DayOfWeek startDay = parseDayOfWeek(startDayName);
        DayOfWeek endDay = parseDayOfWeek(endDayName);

        // Get all days in range
        List<DayOfWeek> daysInRange = getDaysInRange(startDay, endDay);

        if (daysInRange.isEmpty()) {
            return Collections.emptyList();
        }

        // Map each day to its helper count
        Map<DayOfWeek, Integer> helperCountByDay = daysInRange.stream()
                .collect(Collectors.toMap(
                        day -> day,
                        this::countAvailableHelpers
                ));

        // Find maximum helper count
        int maxHelpers = helperCountByDay.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        // Return days with maximum helper count
        return helperCountByDay.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHelpers)
                .map(Map.Entry::getKey)
                .sorted()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to parse day names.
     */
    private DayOfWeek parseDayOfWeek(String dayName) {
        try {
            return DayOfWeek.valueOf(dayName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day name: " + dayName, e);
        }
    }

    /**
     * Helper method to get days in a range.
     */
    public List<DayOfWeek> getDaysInRange(DayOfWeek start, DayOfWeek end) {
        List<DayOfWeek> days = new ArrayList<>();
        DayOfWeek current = start;

        // Handle wrap-around week
        while (true) {
            days.add(current);
            if (current == end) {
                break;
            }
            current = current.plus(1);
            // Prevent infinite loop
            if (days.size() > 7) {
                break;
            }
        }

        return days;
    }

    /**
     * Counts available helpers on a specific day.
     */
    // TODO: Implement countAvailableHelpers method
    private int countAvailableHelpers(DayOfWeek day) {
        int numOfAvailableHelpers = 0;
        for (Brewer brewer : brewers) {
            if (brewer instanceof HelperBrewer && brewer.isAvailableOn(day)) {
                numOfAvailableHelpers++;
            }
        }
        return numOfAvailableHelpers;
    }

    public MasterBrewer getMasterBrewer() {
        for (Brewer brewer : brewers) {
            if (brewer instanceof MasterBrewer) {
                return (MasterBrewer) brewer;
            }
        }
        return null;
    }
}
