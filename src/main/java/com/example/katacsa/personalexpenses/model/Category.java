package com.example.katacsa.personalexpenses.model;

public enum Category {
    FIXED("Fixed"), FLEXIBLE("Flexible"), OCCASIONAL("Occasional");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
