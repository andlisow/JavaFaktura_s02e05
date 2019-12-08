package io.github.javafaktura.s02e05.child.core.model;

public enum Gender {
    MALE("chłopiec"),FEMALE("dziewczynka");

    public final String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender fromName(String name) {
        return name.endsWith("A") ? FEMALE : MALE;
    }

    public String getLabel() {
        return label;
    }

    public static Gender fromSymbol(String symbol) {
        if(symbol.equals("M")) {
            return MALE;
        } else {
            return FEMALE;
        }
    }
}
