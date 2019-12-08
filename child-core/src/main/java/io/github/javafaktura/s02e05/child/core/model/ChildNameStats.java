package io.github.javafaktura.s02e05.child.core.model;

import java.util.Objects;

public class ChildNameStats {
    private String name;
    private int occurrences;
    private Gender gender;

    public ChildNameStats(String name, int occurrences, Gender gender) {
        this.name = name;
        this.occurrences = occurrences;
        this.gender = gender;
    }

    public void incrementOccurrences() {
        this.occurrences++;
    }

    public String getName() {
        return name;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildNameStats childNameStats = (ChildNameStats) o;
        return occurrences == childNameStats.occurrences &&
                Objects.equals(name, childNameStats.name) &&
                gender == childNameStats.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, occurrences, gender);
    }

    @Override
    public String toString() {
        return "ChildNameStats{" +
                "name='" + name + '\'' +
                ", minimalOccurences=" + occurrences +
                ", gender=" + gender +
                '}';
    }
}
