package ru.caloricity.common;

import java.text.DecimalFormat;

public class TwoDigitsFormat {
    private final Double value;

    public TwoDigitsFormat(Double value) {
        this.value = value;
    }

    public Double it() {
        DecimalFormat df = new DecimalFormat("#,##");
        return Double.valueOf(df.format(value));
    }
}
