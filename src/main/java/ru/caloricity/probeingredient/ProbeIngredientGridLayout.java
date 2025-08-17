package ru.caloricity.probeingredient;

import ru.caloricity.common.Updater;
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.probe.Probe;
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
    private final VerticalLayout verticalLayout = new VerticalLayout();
    private final Updater updater;

    public ProbeIngredientGridLayout(List<Ingredient> availableIngredients, Updater updater) {
        this.availableIngredients = availableIngredients;
        this.updater = updater;
    }

    public Component component() {
        Button addButton = new Button("Добавить");
        HorizontalLayout header = new HorizontalLayout(new H3("Ингредиенты"), addButton);
        header.setAlignItems(FlexComponent.Alignment.BASELINE);

        grid.setDeleteHandler(probeIngredient -> {
            probe.getProbeIngredients().remove(probeIngredient);
            probeIngredient.setProbe(null);
            grid.setItems(probe.getProbeIngredients());
            updater.trigger();
        });
        addButton.addClickListener(e -> {
            new ProbeIngredientDialog(availableIngredients, probeIngredient -> {
                probe.getProbeIngredients().add(probeIngredient);
                probeIngredient.setProbe(probe);
                grid.setItems(probe.getProbeIngredients());
                updater.trigger();
            }, grid).open();
        });
        verticalLayout.add(header, grid);

        return verticalLayout;
    }

    public void setProbe(Probe probe) {
        this.probe = probe;
        grid.setItems(probe.getProbeIngredients());
    }
}
