package com.example.demo.common;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class SaveButton extends Button {
    public SaveButton(ComponentEventListener<ClickEvent<Button>> clickListener) {
        super("Сохранить", clickListener);
        addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }
}
