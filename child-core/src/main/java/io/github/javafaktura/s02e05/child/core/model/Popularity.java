package io.github.javafaktura.s02e05.child.core.model;

public enum Popularity {
    MOST_POPULAR(500, Integer.MAX_VALUE, "Najpopularniejsze"),
    POPULAR(100, 499, "Popularne"),
    UNIQUE(10, 99, "Unikalne"),
    VERY_RARE(0, 9, "Bardzo rzadkie");

    private final int minOccurences;
    private final int maxOccurences;
    private final String label;


    Popularity(int minOccurences, int maxOccurences, String label) {
        this.minOccurences = minOccurences;
        this.maxOccurences = maxOccurences;
        this.label = label;
    }

    public int getMinOccurences() {
        return minOccurences;
    }

    public int getMaxOccurences() {
        return maxOccurences;
    }

    public String getLabel() {
        return label;
    }
}
