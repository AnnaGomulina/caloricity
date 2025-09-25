package ru.caloricity.probe.research.fatsresearch;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import ru.caloricity.common.Updater;
import ru.caloricity.probe.research.EmptyResearchForm;
import ru.caloricity.probe.research.ResearchForm;

import java.util.Optional;

public class FatsResearchForm implements ResearchForm<FatsResearch> {
    private final EmptyResearchForm<FatsResearch> emptyResearchForm;
    private final Binder<FatsResearch> binder;
    private final Updater updater;

    public FatsResearchForm(Updater updater) {
        this.updater = updater;
        binder = new Binder<>(FatsResearch.class);
        emptyResearchForm = new EmptyResearchForm<>(binder, updater);
    }

    public FormLayout component() {
        NumberField patronBeforeFirstField = new NumberField("Масса пустого патрона 1 параллель");
        patronBeforeFirstField.setSuffixComponent(new Span("г"));
        binder.forField(patronBeforeFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getPatronMassEmptyParallelFirst,
                FatsResearch::setPatronMassEmptyParallelFirst);

        NumberField patronBeforeSecondField = new NumberField("Масса пустого патрона 2 параллель");
        patronBeforeSecondField.setSuffixComponent(new Span("г"));
        binder.forField(patronBeforeSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getPatronMassEmptyParallelSecond,
                FatsResearch::setPatronMassEmptyParallelSecond);

        FormLayout form = emptyResearchForm.component();
        form.addFormRow(patronBeforeFirstField, patronBeforeSecondField);

        NumberField massNaveskiFirstField = new NumberField("Масса навески 1 параллель");
        massNaveskiFirstField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getMassNaveskiParallelFirst, FatsResearch::setMassNaveskiParallelFirst);

        NumberField massNaveskiSecondField = new NumberField("Масса навески 2 параллель");
        massNaveskiSecondField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getMassNaveskiParallelSecond, FatsResearch::setMassNaveskiParallelSecond);

        form.addFormRow(massNaveskiFirstField, massNaveskiSecondField);

        NumberField patronMassBeforeExtractionParallelFirst = new NumberField("Масса патрона с пробой до экстракции 1 параллель");
        patronMassBeforeExtractionParallelFirst.setSuffixComponent(new Span("г"));
        patronMassBeforeExtractionParallelFirst.setReadOnly(true);
        updater.setPatronMassBeforeExtractionParallelFirst(patronMassBeforeExtractionParallelFirst);

        NumberField patronMassBeforeExtractionParallelSecond = new NumberField("Масса патрона с пробой до экстракции 2 параллель");
        patronMassBeforeExtractionParallelSecond.setSuffixComponent(new Span("г"));
        patronMassBeforeExtractionParallelSecond.setReadOnly(true);
        updater.setPatronMassBeforeExtractionParallelSecond(patronMassBeforeExtractionParallelSecond);

        form.addFormRow(patronMassBeforeExtractionParallelFirst, patronMassBeforeExtractionParallelSecond);

        NumberField patronAfterFirstField = new NumberField("Масса патрона после экстракции 1 параллель");
        patronAfterFirstField.setSuffixComponent(new Span("г"));
        binder.forField(patronAfterFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getPatronMassAfterExtractionParallelFirst,
                FatsResearch::setPatronMassAfterExtractionParallelFirst);

        NumberField patronAfterSecondField = new NumberField("Масса патрона после экстракции 2 параллель");
        patronAfterSecondField.setSuffixComponent(new Span("г"));
        binder.forField(patronAfterSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(FatsResearch::getPatronMassAfterExtractionParallelSecond,
                FatsResearch::setPatronMassAfterExtractionParallelSecond);

        form.addFormRow(patronAfterFirstField, patronAfterSecondField);

        NumberField fatsParallelFirst = new NumberField("Жиры 1 параллель");
        fatsParallelFirst.setSuffixComponent(new Span("г"));
        fatsParallelFirst.setReadOnly(true);
        updater.setFatsParallelFirst(fatsParallelFirst);

        NumberField fatsParallelSecond = new NumberField("Жиры 2 параллель");
        fatsParallelSecond.setSuffixComponent(new Span("г"));
        fatsParallelSecond.setReadOnly(true);
        updater.setFatsParallelSecond(fatsParallelSecond);

        form.addFormRow(fatsParallelFirst, fatsParallelSecond);

        patronBeforeFirstField.addValueChangeListener(e -> updateCalculatedFields());
        patronBeforeSecondField.addValueChangeListener(e -> updateCalculatedFields());
        massNaveskiFirstField.addValueChangeListener(e -> updateCalculatedFields());
        massNaveskiSecondField.addValueChangeListener(e -> updateCalculatedFields());
        patronAfterFirstField.addValueChangeListener(e -> updateCalculatedFields());
        patronAfterSecondField.addValueChangeListener(e -> updateCalculatedFields());

        return form;
    }

    @Override
    public void setResearch(FatsResearch research) {
        emptyResearchForm.setResearch(research);
    }

    @Override
    public Optional<FatsResearch> getResearch() {
        return emptyResearchForm.getResearch();
    }

    @Override
    public void updateCalculatedFields() {
        emptyResearchForm.updateCalculatedFields();
    }
}
