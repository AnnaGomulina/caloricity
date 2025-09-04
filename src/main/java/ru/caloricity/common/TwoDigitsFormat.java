package ru.caloricity.common;

import org.decimal4j.util.DoubleRounder;

public class TwoDigitsFormat {
    private final Double value;

    public TwoDigitsFormat(Double value) {
        this.value = value;
    }

    public Double it() {
        return DoubleRounder.round(value, 2);
    }
}
