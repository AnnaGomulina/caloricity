package ru.caloricity.probe.research.proteinsresearch;

import ru.caloricity.probe.research.EmptyResearchForm;
import ru.caloricity.probe.research.ResearchForm;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;

import java.util.Optional;

public class ProteinsResearchForm implements ResearchForm<ProteinsResearch> {
    private final EmptyResearchForm<ProteinsResearch> emptyResearchForm;
    private final Binder<ProteinsResearch> binder;

    public ProteinsResearchForm() {
        binder = new Binder<>(ProteinsResearch.class);
        emptyResearchForm = new EmptyResearchForm<>(binder);
    }

    public FormLayout component() {
        FormLayout form = emptyResearchForm.component();

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

        form.addFormRow(massNaveskiFirstField, massNaveskiSecondField);

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

        form.addFormRow(titrantVolumeFirstField, titrantVolumeSecondField);

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

        form.addFormRow(controlVolumeField, coefficientField);

        return form;
    }

    public void setResearch(ProteinsResearch research) {
        emptyResearchForm.setResearch(research);
    }

    public Optional<ProteinsResearch> getResearch() {
        return emptyResearchForm.getResearch();
    }
}