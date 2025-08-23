package ru.caloricity.ingredient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IngredientType {
    /** Смешанный */
    MIXED(1 - 0.06, 1 - 0.12, 1 - 0.09),
    /** Животный */
    ANIMAL(1 - 0.08, 1 - 0.25, 1 - 0),
    /** Растительный */
    VEGETABLE(1 - 0.05, 1 - 0.09, 1 - 0.09);

    public final double coefficientOfLossesForProtein;
    public final double coefficientOfLossesForFat;
    public final double coefficientOfLossesForCarbohydrates;
}
