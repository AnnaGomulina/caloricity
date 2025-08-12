package com.example.demo.probeingredient;

import com.example.demo.ingredient.Ingredient;
import com.example.demo.probe.Probe;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class ProbeIngredientGridLayout {
    private Probe probe;
    private final ProbeIngredientGrid grid = new ProbeIngredientGrid();
    private final List<Ingredient> availableIngredients;

    public ProbeIngredientGridLayout(List<Ingredient> availableIngredients) {
        this.availableIngredients = availableIngredients;
    }

    public Component component() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Button addButton = new Button("Добавить");
        HorizontalLayout header = new HorizontalLayout(new H3("Ингредиенты"), addButton);
        header.setAlignItems(FlexComponent.Alignment.BASELINE);

        grid.setDeleteHandler(probeIngredient -> {
            probe.getProbeIngredients().remove(probeIngredient);
            probeIngredient.setProbe(null);
            grid.setItems(probe.getProbeIngredients());
        });
        addButton.addClickListener(e -> {
            new ProbeIngredientDialog(availableIngredients, probeIngredient -> {
                probe.getProbeIngredients().add(probeIngredient);
                probeIngredient.setProbe(probe);
                grid.setItems(probe.getProbeIngredients());
            }).open();
        });
        verticalLayout.add(header, grid);

        return verticalLayout;
    }

    public void setProbe(Probe probe) {
        this.probe = probe;
        grid.setItems(probe.getProbeIngredients());
    }
}
