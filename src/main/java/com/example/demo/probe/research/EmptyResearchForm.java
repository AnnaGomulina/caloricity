package com.example.demo.probe.research;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.Binder;

import java.util.Optional;

public class EmptyResearchForm<T> implements ResearchForm<T> {
    private T formDataObject;
    private final Binder<T> binder;

    public EmptyResearchForm(Binder<T> binder) {
        this.binder = binder;
    }

    public FormLayout component() {
        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.setExpandFields(true);
        formLayout.setExpandColumns(true);
        return formLayout;
    }

    @Override
    public void setResearch(T research) {
        this.formDataObject = research;
        binder.readBean(research);
    }

    @Override
    public Optional<T> getResearch() {
        return Optional.ofNullable(formDataObject)
            .filter(binder::writeBeanIfValid);
    }
}
