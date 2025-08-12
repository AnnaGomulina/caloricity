package com.example.demo.probeingredient;

import com.example.demo.ingredient.Ingredient;
import com.example.demo.probe.Probe;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.Optional;

public class ProbeIngredientGridLayout extends VerticalLayout {
    private Optional<Probe> probe = Optional.empty();
    private final ProbeIngredientGrid grid;

    public ProbeIngredientGridLayout(List<Ingredient> availableIngredients) {
        Button addButton = new Button("Добавить");
        HorizontalLayout header = new HorizontalLayout(new H3("Ингредиенты"), addButton);
        header.setAlignItems(Alignment.BASELINE);
        add(header);
        grid = new ProbeIngredientGrid();
        grid.setDeleteHandler(probeIngredient -> {
            probe.ifPresent(p -> p.getProbeIngredients().remove(probeIngredient));
            probeIngredient.setProbe(null);
            probe.map(Probe::getProbeIngredients).ifPresent(grid::setItems);
        });
        probe.map(Probe::getProbeIngredients).ifPresent(grid::setItems);
        addButton.addClickListener(e -> {
            new ProbeIngredientDialog(availableIngredients, probeIngredient -> {
                probe.map(Probe::getProbeIngredients).ifPresent(p -> p.add(probeIngredient));
                probe.ifPresent(probeIngredient::setProbe);
                probe.map(Probe::getProbeIngredients).ifPresent(grid::setItems);
            }).open();
        });
        add(grid);
    }

    public void setProbe(Probe probe) {
        this.probe = Optional.ofNullable(probe);
        grid.setItems(probe.getProbeIngredients());
    }
}
