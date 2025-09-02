package ru.caloricity.common;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FourDigitsFormat {
    private final Double value;

    public FourDigitsFormat(Double value) {
        this.value = value;
    }

    public Double it() {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return Double.valueOf(df.format(value));
    }
}
