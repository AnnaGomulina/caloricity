package com.example.demo.common;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;

public class CancelButton extends Button {
    public CancelButton(ComponentEventListener<ClickEvent<Button>> clickListener) {
        super("Отмена", clickListener);
    }
}
