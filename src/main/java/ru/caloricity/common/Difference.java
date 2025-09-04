package ru.caloricity.common;

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
        Double val = (first - second) / second * 100;
        return new TwoDigitsFormat(val).it();
    }
}
