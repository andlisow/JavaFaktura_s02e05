package io.github.javafaktura.s02e05.child.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ParentPreferences {

    private Gender gender;
    private Popularity popularity;

    public List<Predicate<ChildNameStats>> asPredicates() {
        List<Predicate<ChildNameStats>> predicates = new ArrayList<>();

        if (gender != null) {
            predicates.add(c -> c.getGender() == gender);
        }
        if (popularity != null) {
            predicates.add(c -> c.getOccurrences() > popularity.getMinOccurences());
            predicates.add(c -> c.getOccurrences() < popularity.getMaxOccurences());
        }
        return predicates;
    }

    public ParentPreferences(Gender gender) {
        this.gender = gender;
    }

    public ParentPreferences(Gender gender, Popularity popularity) {
        this.gender = gender;
        this.popularity = popularity;
    }

    public Gender getGender() {
        return gender;
    }

    public Popularity getPopularity() {
        return popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentPreferences)) return false;
        ParentPreferences that = (ParentPreferences) o;
        return gender == that.gender &&
                popularity == that.popularity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, popularity);
    }

    @Override
    public String toString() {
        return "ParentPreferences{" +
                "gender=" + gender +
                ", popularity=" + popularity +
                '}';
    }
}
