package com.example.demo.probeingredient;

import com.example.demo.common.CancelButton;
import com.example.demo.common.SaveButton;
import com.example.demo.ingredient.Ingredient;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.function.SerializableConsumer;

import java.util.List;

public class ProbeIngredientDialog extends Dialog {
    private final SerializableConsumer<ProbeIngredient> onSaveCallback;
    private final ProbeIngredientForm form;
    private final ProbeIngredientGrid grid;

    public ProbeIngredientDialog(List<Ingredient> availableIngredients, SerializableConsumer<ProbeIngredient> onSaveCallback, ProbeIngredientGrid grid) {
        this.onSaveCallback = onSaveCallback;
        this.grid = grid;

        setHeaderTitle("Добавить ингредиент");
        form = new ProbeIngredientForm(availableIngredients);
        form.set(new ProbeIngredient());

        add(form);
        getFooter().add(
            new CancelButton(event -> close()),
            new SaveButton(this::save)
        );
    }

    private void save(ClickEvent<Button> event) {
        form.get().ifPresent(probeIngredient -> {
            onSaveCallback.accept(probeIngredient);
            close();
            grid.select(probeIngredient);
        });
    }
}