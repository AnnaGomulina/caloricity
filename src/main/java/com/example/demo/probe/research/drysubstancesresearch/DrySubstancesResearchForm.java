package com.example.demo.probe.research.drysubstancesresearch;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;

import java.util.Optional;

public class DrySubstancesResearchForm extends FormLayout {
    private DrySubstancesResearch formDataObject;
    private final Binder<DrySubstancesResearch> binder;

    public DrySubstancesResearchForm() {
        binder = new Binder<>(DrySubstancesResearch.class);

        NumberField byuksaFirstField = new NumberField("Масса бюксы 1 параллель");
        byuksaFirstField.setSuffixComponent(new Span("г"));
        binder.forField(byuksaFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getByuksaParallelFirst, DrySubstancesResearch::setByuksaParallelFirst);
        add(byuksaFirstField);

        NumberField byuksaSecondField = new NumberField("Масса бюксы 2 параллель");
        byuksaSecondField.setSuffixComponent(new Span("г"));
        binder.forField(byuksaSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getByuksaParallelSecond, DrySubstancesResearch::setByuksaParallelSecond);
        add(byuksaSecondField);

        NumberField massNaveskiFirstField = new NumberField("Масса навески 1 параллель");
        massNaveskiFirstField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getMassNaveskiParallelFirst, DrySubstancesResearch::setMassNaveskiParallelFirst);
        add(massNaveskiFirstField);

        NumberField massNaveskiSecondField = new NumberField("Масса навески 2 параллель");
        massNaveskiSecondField.setSuffixComponent(new Span("г"));
        binder.forField(massNaveskiSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getMassNaveskiParallelSecond, DrySubstancesResearch::setMassNaveskiParallelSecond);
        add(massNaveskiSecondField);

        NumberField afterDryingFirstField = new NumberField("Масса после высушивания 1 параллель");
        afterDryingFirstField.setSuffixComponent(new Span("г"));
        binder.forField(afterDryingFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getByuksaAfterDryingParallelFirst, DrySubstancesResearch::setByuksaAfterDryingParallelFirst);
        add(afterDryingFirstField);

        NumberField afterDryingSecondField = new NumberField("Масса после высушивания 2 параллель");
        afterDryingSecondField.setSuffixComponent(new Span("г"));
        binder.forField(afterDryingSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(DrySubstancesResearch::getByuksaAfterDryingParallelSecond, DrySubstancesResearch::setByuksaAfterDryingParallelSecond);
        add(afterDryingSecondField);
    }

    public void setFormDataObject(DrySubstancesResearch research) {
        this.formDataObject = research;
        binder.readBean(research);
    }

    public Optional<DrySubstancesResearch> getFormDataObject() {
        return binder.writeBeanIfValid(formDataObject)
            ? Optional.of(formDataObject)
            : Optional.empty();
    }
}