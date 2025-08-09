package com.example.demo.ingredient;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.function.SerializableConsumer;

public class IngredientDialog extends Dialog {
    private final SerializableConsumer<Ingredient> onSaveCallback;
    private final IngredientForm form;

    public IngredientDialog(SerializableConsumer<Ingredient> onSaveCallback) {
        setHeaderTitle("Добавить ингредиент");
        this.onSaveCallback = onSaveCallback;

        form = new IngredientForm();

        var saveBtn = new Button("Сохранить", this::save);
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var cancelBtn = new Button("Отмена", event -> close());
        cancelBtn.getStyle().set("margin-right", "auto"); // Поместить кнопку слева

        add(form);
        getFooter().add(cancelBtn, saveBtn);
    }

    private void save(ClickEvent<Button> event)  {
        form.getFormDataObject().ifPresent(ingredient -> {
            onSaveCallback.accept(ingredient);
            close();
        });
    }
}
