package ru.caloricity.ingredient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IngredientType {
    /** Смешанный */
    MIXED(0.06, 0.12, 0.09),
    /** Животный */
    ANIMAL(0.08, 0.25, 0),
    /** Растительный */
    VEGETABLE(0.05, 0.09, 0.09);

    public final double coefficientOfLossesForProtein;
    public final double coefficientOfLossesForFat;
    public final double coefficientOfLossesForCarbohydrates;
}
