package com.example.demo.ingredient;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class IngredientGrid extends Grid<Ingredient> {

    public IngredientGrid() {
        super(Ingredient.class, false);
        addColumn(Ingredient::getName).setAutoWidth(true).setHeader("Наименование ингредиента").setTextAlign(ColumnTextAlign.CENTER);
        addColumn(Ingredient::getEdiblePart).setAutoWidth(true).setHeader("Съедобная часть, доля").setTextAlign(ColumnTextAlign.CENTER);
        addColumn(Ingredient::getWater).setAutoWidth(true).setHeader("Масса воды, г").setTextAlign(ColumnTextAlign.CENTER);
        addColumn(Ingredient::getProteins).setAutoWidth(true).setHeader("Масса белков, г").setTextAlign(ColumnTextAlign.CENTER);
        addColumn(Ingredient::getFats).setAutoWidth(true).setHeader("Масса жиров, г").setTextAlign(ColumnTextAlign.CENTER);
        addColumn(Ingredient::getCarbohydrates).setAutoWidth(true).setHeader("Масса углеводов, г").setTextAlign(ColumnTextAlign.CENTER);
        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
    }
}
