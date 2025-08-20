package ru.caloricity.ingredient;

import com.vaadin.flow.component.html.Span;

import java.util.Map;

public class IngredientTypeChip extends Span {
    private static final Map<IngredientType, String> toRussianMap = Map.of(
        IngredientType.MIXED, "Смешанный",
        IngredientType.ANIMAL, "Животный",
        IngredientType.VEGETABLE, "Растительный"
    );

    public IngredientTypeChip(IngredientType probeType) {
        super(toRussianMap.get(probeType));
    }
}
