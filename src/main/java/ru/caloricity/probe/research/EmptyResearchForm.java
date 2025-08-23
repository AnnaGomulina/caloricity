package ru.caloricity.probe.research;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.Binder;
import ru.caloricity.common.Updater;

import java.util.Optional;

public class EmptyResearchForm<T> implements ResearchForm<T> {
    private T formDataObject;
    private final Binder<T> binder;
    private final Updater updater;

    public EmptyResearchForm(Binder<T> binder, Updater updater) {
        this.binder = binder;
        this.updater = updater;
    }

    public FormLayout component() {
        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.setExpandFields(true);
        formLayout.setExpandColumns(true);
        return formLayout;
    }

    public void updateCalculatedFields() {
        binder.writeBeanAsDraft(formDataObject);
        updater.trigger();
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
