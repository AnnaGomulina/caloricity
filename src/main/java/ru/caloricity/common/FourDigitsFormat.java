package ru.caloricity.common;

import org.decimal4j.util.DoubleRounder;

public class FourDigitsFormat {
    private final Double value;

    public FourDigitsFormat(Double value) {
        this.value = value;
    }

    public Double it() {
        return DoubleRounder.round(value, 4);
    }
}
