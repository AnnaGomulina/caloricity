package com.example.demo.probe.research.fatsresearch;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;

import java.util.Optional;

public class FatsResearchForm extends FormLayout {
    private FatsResearch formDataObject;
    private final Binder<FatsResearch> binder;

    public FatsResearchForm() {
        setAutoResponsive(true);
        setExpandFields(true);
        setExpandColumns(true);
        binder = new Binder<>(FatsResearch.class);

        NumberField patronBeforeFirstField = new NumberField("Масса патрона до экстракции 1 параллель");
        patronBeforeFirstField.setSuffixComponent(new Span("г"));
        binder.forField(patronBeforeFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getPatronMassBeforeExtractionParallelFirst,
                FatsResearch::setPatronMassBeforeExtractionParallelFirst);

        NumberField patronBeforeSecondField = new NumberField("Масса патрона до экстракции 2 параллель");
        patronBeforeSecondField.setSuffixComponent(new Span("г"));
        binder.forField(patronBeforeSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getPatronMassBeforeExtractionParallelSecond,
                FatsResearch::setPatronMassBeforeExtractionParallelSecond);

        addFormRow(patronBeforeFirstField, patronBeforeSecondField);

        NumberField patronAfterFirstField = new NumberField("Масса патрона после экстракции 1 параллель");
        patronAfterFirstField.setSuffixComponent(new Span("г"));
        binder.forField(patronAfterFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .withValidator(value -> value < patronBeforeFirstField.getValue(),
                "Должно быть меньше массы до экстракции")
            .bind(FatsResearch::getPatronMassAfterExtractionParallelFirst,
                FatsResearch::setPatronMassAfterExtractionParallelFirst);

        NumberField patronAfterSecondField = new NumberField("Масса патрона после экстракции 2 параллель");
        patronAfterSecondField.setSuffixComponent(new Span("г"));
        binder.forField(patronAfterSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .withValidator(value -> value < patronBeforeSecondField.getValue(),
                "Должно быть меньше массы до экстракции")
            .bind(FatsResearch::getPatronMassAfterExtractionParallelSecond,
                FatsResearch::setPatronMassAfterExtractionParallelSecond);

        addFormRow(patronAfterFirstField, patronAfterSecondField);
    }

    public void setFormDataObject(FatsResearch fatsResearch) {
        this.formDataObject = fatsResearch;
        binder.readBean(fatsResearch);
    }

    public Optional<FatsResearch> getFormDataObject() {
        return Optional.ofNullable(formDataObject)
            .filter(binder::writeBeanIfValid);
    }
}
