package io.github.javafaktura.s02e05.child.core.model;

import java.time.Year;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ChildNameHistoricalStats {
    private String name;
    private Gender gender;
    private Map<Year, Integer> stats;


    public ChildNameHistoricalStats(String name, Gender gender, Map<Year, Integer> stats) {
        this.name = name;
        this.gender = gender;
        this.stats = stats;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Map<Year, Integer> getStats() {
        return stats;
    }

    public int getStatsForYear(Year year) {
        return stats.getOrDefault(year, 0);
    }

    public Integer getLastMostPopularYear() {
        if(stats.isEmpty()) {
            return 0;
        }
        return getMaxEntry(stats).getKey().getValue();
    }

    private static Map.Entry<Year, Integer> getMaxEntry(Map<Year, Integer> map) {
        Map.Entry<Year, Integer> maxEntry = null;
        Integer max = Collections.max(map.values());

        for(Map.Entry<Year, Integer> entry : map.entrySet()) {
            Integer value = entry.getValue();

            if(null != value && max == value) {
                maxEntry = entry;
            }
        }

        return maxEntry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildNameHistoricalStats that = (ChildNameHistoricalStats) o;
        return Objects.equals(name, that.name) &&
                gender == that.gender &&
                Objects.equals(stats, that.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, stats);
    }
}
