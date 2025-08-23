package ru.caloricity.common;

import java.text.DecimalFormat;

public class FourDigitsFormat {
    private final Double value;

    public FourDigitsFormat(Double value) {
        this.value = value;
    }

    public Double it() {
        return Double.valueOf(new DecimalFormat("#.####").format(value));
    }
}
