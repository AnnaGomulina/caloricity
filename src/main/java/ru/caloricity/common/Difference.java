package ru.caloricity.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Difference {
    private final Double first;
    private final Double second;

    public Difference(Double first, Double second) {
        this.first = first;
        this.second = second;
    }

    public Double get() {
        if (new AnyNull(first, second).is() || Math.abs(second) < 1e-6) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return Double.valueOf(new DecimalFormat("#.##").format((first - second) / second * 100));
    }
}
