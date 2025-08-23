package ru.caloricity.probe.research.drysubstancesresearch;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import ru.caloricity.common.Updater;
import ru.caloricity.probe.research.EmptyResearchForm;
import ru.caloricity.probe.research.ResearchForm;

import java.util.Optional;

public class DrySubstancesResearchForm implements ResearchForm<DrySubstancesResearch> {
    private final EmptyResearchForm<DrySubstancesResearch> emptyResearchForm;
    private final Binder<DrySubstancesResearch> binder;

    public DrySubstancesResearchForm(Updater updater) {
        binder = new Binder<>(DrySubstancesResearch.class);
        emptyResearchForm = new EmptyResearchForm<>(binder, updater);
    }

    public FormLayout component() {
        NumberField byuksaFirstField = new NumberField("Масса бюксы 1 параллель");
        byuksaFirstField.setSuffixComponent(new Span("г"));
        binder.forField(byuksaFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getByuksaParallelFirst, DrySubstancesResearch::setByuksaParallelFirst);

        NumberField byuksaSecondField = new NumberField("Масса бюксы 2 параллель");
        byuksaSecondField.setSuffixComponent(new Span("г"));
        binder.forField(byuksaSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getByuksaParallelSecond, DrySubstancesResearch::setByuksaParallelSecond);

        FormLayout form = emptyResearchForm.component();
        form.addFormRow(byuksaFirstField, byuksaSecondField);

        NumberField massNaveskiFirstField = new NumberField("Масса навески 1 параллель");
        massNaveskiFirstField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getMassNaveskiParallelFirst, DrySubstancesResearch::setMassNaveskiParallelFirst);

        NumberField massNaveskiSecondField = new NumberField("Масса навески 2 параллель");
        massNaveskiSecondField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getMassNaveskiParallelSecond, DrySubstancesResearch::setMassNaveskiParallelSecond);

        form.addFormRow(massNaveskiFirstField, massNaveskiSecondField);

        NumberField afterDryingFirstField = new NumberField("Масса после высушивания 1 параллель");
        afterDryingFirstField.setSuffixComponent(new Span("г"));
        binder.forField(afterDryingFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getByuksaAfterDryingParallelFirst, DrySubstancesResearch::setByuksaAfterDryingParallelFirst);

        NumberField afterDryingSecondField = new NumberField("Масса после высушивания 2 параллель");
        afterDryingSecondField.setSuffixComponent(new Span("г"));
        binder.forField(afterDryingSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getByuksaAfterDryingParallelSecond, DrySubstancesResearch::setByuksaAfterDryingParallelSecond);

        form.addFormRow(afterDryingFirstField, afterDryingSecondField);

        byuksaFirstField.addValueChangeListener(e -> updateCalculatedFields());
        byuksaSecondField.addValueChangeListener(e -> updateCalculatedFields());
        massNaveskiFirstField.addValueChangeListener(e -> updateCalculatedFields());
        massNaveskiSecondField.addValueChangeListener(e -> updateCalculatedFields());
        afterDryingFirstField.addValueChangeListener(e -> updateCalculatedFields());
        afterDryingSecondField.addValueChangeListener(e -> updateCalculatedFields());

        return form;
    }

    @Override
    public void setResearch(DrySubstancesResearch research) {
        emptyResearchForm.setResearch(research);
    }

    @Override
    public Optional<DrySubstancesResearch> getResearch() {
        return emptyResearchForm.getResearch();
    }

    @Override
    public void updateCalculatedFields() {
        emptyResearchForm.updateCalculatedFields();
    }
}