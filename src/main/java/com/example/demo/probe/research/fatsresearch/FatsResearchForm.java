package com.example.demo.probe.research.fatsresearch;

import com.example.demo.probe.research.ResearchForm;
import com.example.demo.probe.research.EmptyResearchForm;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;

import java.util.Optional;

public class FatsResearchForm implements ResearchForm<FatsResearch> {
    private final EmptyResearchForm<FatsResearch> emptyResearchForm;
    private final Binder<FatsResearch> binder;
    private NumberField massFirstParallelField;
    private NumberField massSecondParallelField;

    public FatsResearchForm() {
        binder = new Binder<>(FatsResearch.class);
        emptyResearchForm = new EmptyResearchForm<>(binder);
    }

    public FormLayout component() {
        FormLayout form = emptyResearchForm.component();

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

        form.addFormRow(patronBeforeFirstField, patronBeforeSecondField);

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

        form.addFormRow(patronAfterFirstField, patronAfterSecondField);

        massFirstParallelField = new NumberField("Масса жира 1 параллель");
        massFirstParallelField.setSuffixComponent(new Span("г"));
        massFirstParallelField.setReadOnly(true);
        binder.forField(massFirstParallelField)
            .bind(FatsResearch::getMassParallelFirst, null);

        massSecondParallelField = new NumberField("Масса жира 2 параллель");
        massSecondParallelField.setSuffixComponent(new Span("г"));
        massSecondParallelField.setReadOnly(true);
        binder.forField(massSecondParallelField)
            .bind(FatsResearch::getMassParallelSecond, null);

        form.addFormRow(massFirstParallelField, massSecondParallelField);

        patronBeforeFirstField.addValueChangeListener(e -> updateCalculatedFields());
        patronBeforeSecondField.addValueChangeListener(e -> updateCalculatedFields());
        patronAfterFirstField.addValueChangeListener(e -> updateCalculatedFields());
        patronAfterSecondField.addValueChangeListener(e -> updateCalculatedFields());

        return form;
    }


    @Override
    public void set(FatsResearch research) {
        emptyResearchForm.set(research);
    }

    @Override
    public Optional<FatsResearch> get() {
        return emptyResearchForm.get();
    }

    private void updateCalculatedFields() {
        get().ifPresent(r -> {
            massFirstParallelField.setValue(r.getMassParallelFirst());
            massSecondParallelField.setValue(r.getMassParallelSecond());
        });
    }
}
