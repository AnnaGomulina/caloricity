package ru.caloricity.probeingredient;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.function.SerializableConsumer;
import ru.caloricity.common.CancelButton;
import ru.caloricity.common.SaveButton;
import ru.caloricity.ingredient.Ingredient;

import java.util.List;

public class ProbeIngredientDialog extends Dialog {
    private final SerializableConsumer<ProbeIngredient> onSaveCallback;
    private final ProbeIngredientForm form;

    public ProbeIngredientDialog(List<Ingredient> availableIngredients, SerializableConsumer<ProbeIngredient> onSaveCallback) {
        this.onSaveCallback = onSaveCallback;

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
        });
    }
}