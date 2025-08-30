package ru.caloricity.probe;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProbeType {
    FIRST(0.012, 0.85, "Первое"),
    SECOND(0.01, 0.9, "Второе"),
    THIRD(0.001, 0.9, "Третье");

    public final double coefficientOfMinerals;
    public final double coefficientOfLossesForDrySubstances;
    public final String russianName;
}
