package com.example.demo.ingredient;

import com.example.demo.common.CancelButton;
import com.example.demo.common.SaveButton;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.function.SerializableConsumer;

public class IngredientDialog extends Dialog {
    private final SerializableConsumer<Ingredient> onSaveCallback;
    private final IngredientForm form;

    public IngredientDialog(SerializableConsumer<Ingredient> onSaveCallback) {
        setHeaderTitle("Добавить ингредиент");
        this.onSaveCallback = onSaveCallback;
        form = new IngredientForm();
        form.setFormDataObject(new Ingredient());
        add(form);
        getFooter().add(new CancelButton(event -> close()), new SaveButton(this::save));
    }

    private void save(ClickEvent<Button> event)  {
        form.get().ifPresent(ingredient -> {
            onSaveCallback.accept(ingredient);
            close();
        });
    }
}
