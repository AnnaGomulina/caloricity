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
        binder = new Binder<>(FatsResearch.class);

        NumberField massNaveskiFirstField = new NumberField("Масса навески первая параллель");
        massNaveskiFirstField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getMassNaveskiParallelFirst,
                FatsResearch::setMassNaveskiParallelFirst);
        add(massNaveskiFirstField);

        NumberField massNaveskiSecondField = new NumberField("Масса навески вторая параллель");
        massNaveskiSecondField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getMassNaveskiParallelSecond,
                FatsResearch::setMassNaveskiParallelSecond);
        add(massNaveskiSecondField);

        NumberField patronBeforeFirstField = new NumberField("Масса патрона до экстракции первая параллель");
        patronBeforeFirstField.setSuffixComponent(new Span("г"));
        binder.forField(patronBeforeFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getPatronMassBeforeExtractionParallelFirst,
                FatsResearch::setPatronMassBeforeExtractionParallelFirst);
        add(patronBeforeFirstField);

        NumberField patronBeforeSecondField = new NumberField("Масса патрона до экстракции вторая параллель");
        patronBeforeSecondField.setSuffixComponent(new Span("г"));
        binder.forField(patronBeforeSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getPatronMassBeforeExtractionParallelSecond,
                FatsResearch::setPatronMassBeforeExtractionParallelSecond);
        add(patronBeforeSecondField);

        NumberField patronAfterFirstField = new NumberField("Масса патрона после экстракции первая параллель");
        patronAfterFirstField.setSuffixComponent(new Span("г"));
        binder.forField(patronAfterFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .withValidator(value -> value < patronBeforeFirstField.getValue(),
                "Должно быть меньше массы до экстракции")
            .bind(FatsResearch::getPatronMassAfterExtractionParallelFirst,
                FatsResearch::setPatronMassAfterExtractionParallelFirst);
        add(patronAfterFirstField);

        NumberField patronAfterSecondField = new NumberField("Масса патрона после экстракции вторая параллель");
        patronAfterSecondField.setSuffixComponent(new Span("г"));
        binder.forField(patronAfterSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .withValidator(value -> value < patronBeforeSecondField.getValue(),
                "Должно быть меньше массы до экстракции")
            .bind(FatsResearch::getPatronMassAfterExtractionParallelSecond,
                FatsResearch::setPatronMassAfterExtractionParallelSecond);
        add(patronAfterSecondField);
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
