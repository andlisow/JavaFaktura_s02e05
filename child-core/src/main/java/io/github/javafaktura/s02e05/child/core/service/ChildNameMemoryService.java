package io.github.javafaktura.s02e05.child.core.service;

import io.github.javafaktura.s02e05.child.core.model.ChildNameHistoricalStats;
import io.github.javafaktura.s02e05.child.core.model.ChildNameStats;
import io.github.javafaktura.s02e05.child.core.model.Gender;
import io.github.javafaktura.s02e05.child.core.model.ParentPreferences;
import io.github.javafaktura.s02e05.child.core.provider.ChildNameHistoricalStatsProvider;
import io.github.javafaktura.s02e05.child.core.provider.ChildNameStatsProvider;

import java.time.Year;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ChildNameMemoryService implements ChildNameService{
    private List<ChildNameStats> currentYearStats;
    private List<ChildNameHistoricalStats> historicalStats;

    public ChildNameMemoryService(ChildNameStatsProvider childNameStatsProvider,
                                  ChildNameHistoricalStatsProvider childNameHistoricalStatsProvider) {
        this.currentYearStats = childNameStatsProvider.load();
        this.historicalStats = childNameHistoricalStatsProvider.load();
    }

    public List<ChildNameStats> getAll() {
        return currentYearStats;
    }

    public List<ChildNameStats> getAll(ParentPreferences preferences) {
        return filter(currentYearStats, preferences.asPredicates());
    }

    public int countAllOccurrences() {
        return currentYearStats.stream()
                .map(n -> Integer.valueOf(n.getOccurrences()))
                .reduce(0, Integer::sum);
    }

    public ChildNameStats add(String name) {
        for (ChildNameStats childNameStats : currentYearStats) {
            if (childNameStats.getName().equalsIgnoreCase(name)) {
                childNameStats.incrementOccurrences();
                return childNameStats;
            }
        }
        ChildNameStats newName = new ChildNameStats(name.toUpperCase(), 1, Gender.fromName(name));
        currentYearStats.add(newName);
        return newName;
    }

    public ChildNameStats getRandom() {
        return getShuffledCopy(currentYearStats).get(0);
    }

    public ChildNameStats getRandom(ParentPreferences preferences) {
        List<ChildNameStats> filtered = filter(getShuffledCopy(currentYearStats), preferences.asPredicates());
        return filtered.get(0);
    }

    public ChildNameStats lookFor(String name) {
        return currentYearStats.stream()
                .filter(c -> c.getName().equals(name.toUpperCase()))
                .findAny()
                .orElse(new ChildNameStats(name, 0, Gender.fromName(name)));
    }

    @Override
    public Optional<ChildNameHistoricalStats> getHistoricalStats(String name) {
        return historicalStats.stream()
                .filter(c -> c.getName().equals(name.toUpperCase()))
                .findAny()
                .or(Optional::empty);
    }

    private Map<Year, Integer> getHistoricalStatsSummary() {
        return historicalStats.stream()
                .map(c -> c.getStats())
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1 + v2));
    }

    @Override
    public Integer getHistoricalOccurences(String name, Year year) {
        return getHistoricalStats(name)
                .orElse(new ChildNameHistoricalStats(name, Gender.fromName(name), Collections.emptyMap()))
                .getStatsForYear(year);
    }

    private List<ChildNameStats> filter(List<ChildNameStats> fullList, List<Predicate<ChildNameStats>> predicates) {
        return fullList
                .stream()
                .filter(predicates.stream().reduce(x -> true, Predicate::and))
                .collect(Collectors.toList());
    }

    private List<ChildNameStats> getShuffledCopy(List<ChildNameStats> source) {
        List<ChildNameStats> destination = new ArrayList<>(source);
        Collections.copy(destination, source);
        Collections.shuffle(destination);
        return destination;
    }
}