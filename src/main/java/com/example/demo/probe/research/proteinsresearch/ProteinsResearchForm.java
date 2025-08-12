package com.example.demo.probe.research.proteinsresearch;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;

import java.util.Optional;

public class ProteinsResearchForm extends FormLayout {
    private ProteinsResearch formDataObject;
    private final Binder<ProteinsResearch> binder;

    public ProteinsResearchForm() {
        setAutoResponsive(true);
        setExpandFields(true);
        setExpandColumns(true);
        binder = new Binder<>(ProteinsResearch.class);

        NumberField massNaveskiFirstField = new NumberField("Масса навески 1 параллель");
        massNaveskiFirstField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(ProteinsResearch::getMassNaveskiParallelFirst, ProteinsResearch::setMassNaveskiParallelFirst);

        NumberField massNaveskiSecondField = new NumberField("Масса навески 2 параллель");
        massNaveskiSecondField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(ProteinsResearch::getMassNaveskiParallelSecond, ProteinsResearch::setMassNaveskiParallelSecond);

        addFormRow(massNaveskiFirstField, massNaveskiSecondField);

        NumberField titrantVolumeFirstField = new NumberField("Объём титранта 1 параллель");
        titrantVolumeFirstField.setSuffixComponent(new Span("г/см³"));
        binder.forField(titrantVolumeFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(ProteinsResearch::getTitrantVolumeParallelFirst, ProteinsResearch::setTitrantVolumeParallelFirst);

        NumberField titrantVolumeSecondField = new NumberField("Объём титранта 2 параллель");
        titrantVolumeSecondField.setSuffixComponent(new Span("г/см³"));
        binder.forField(titrantVolumeSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(ProteinsResearch::getTitrantVolumeParallelSecond, ProteinsResearch::setTitrantVolumeParallelSecond);

        addFormRow(titrantVolumeFirstField, titrantVolumeSecondField);

        NumberField controlVolumeField = new NumberField("Объём контроля");
        controlVolumeField.setSuffixComponent(new Span("г/см³"));
        binder.forField(controlVolumeField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(ProteinsResearch::getControlVolume, ProteinsResearch::setControlVolume);

        NumberField coefficientField = new NumberField("Коэффициент");
        binder.forField(coefficientField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть от 0 до 1", 0., 1.))
            .bind(ProteinsResearch::getCoefficient, ProteinsResearch::setCoefficient);

        addFormRow(controlVolumeField, coefficientField);
    }

    public void setFormDataObject(ProteinsResearch research) {
        this.formDataObject = research;
        binder.readBean(research);
    }

    public Optional<ProteinsResearch> getFormDataObject() {
        return Optional.ofNullable(formDataObject)
            .filter(binder::writeBeanIfValid);
    }
}