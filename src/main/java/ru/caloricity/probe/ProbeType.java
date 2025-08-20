package ru.caloricity.probe;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProbeType {
    FIRST(0.012, 0.85),
    SECOND(0.01, 0.9),
    THIRD(0.001, 0.9);

    public final double coefficientOfMinerals;
    public final double coefficientOfLossesForDrySubstances;
}
