package ru.caloricity.probe.research.proteinsresearch;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import ru.caloricity.common.Updater;
import ru.caloricity.probe.research.EmptyResearchForm;
import ru.caloricity.probe.research.ResearchForm;

import java.util.Optional;

public class ProteinsResearchForm implements ResearchForm<ProteinsResearch> {
    private final EmptyResearchForm<ProteinsResearch> emptyResearchForm;
    private final Binder<ProteinsResearch> binder;

    public ProteinsResearchForm(Updater updater) {
        binder = new Binder<>(ProteinsResearch.class);
        emptyResearchForm = new EmptyResearchForm<>(binder, updater);
    }

    public FormLayout component() {
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

        FormLayout form = emptyResearchForm.component();
        form.addFormRow(massNaveskiFirstField, massNaveskiSecondField);

        NumberField titrantVolumeFirstField = new NumberField("Объём титранта 1 параллель");
        titrantVolumeFirstField.setSuffixComponent(new Span("см³"));
        binder.forField(titrantVolumeFirstField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(ProteinsResearch::getTitrantVolumeParallelFirst, ProteinsResearch::setTitrantVolumeParallelFirst);

        NumberField titrantVolumeSecondField = new NumberField("Объём титранта 2 параллель");
        titrantVolumeSecondField.setSuffixComponent(new Span("см³"));
        binder.forField(titrantVolumeSecondField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0., Double.MAX_VALUE))
            .bind(ProteinsResearch::getTitrantVolumeParallelSecond, ProteinsResearch::setTitrantVolumeParallelSecond);

        form.addFormRow(titrantVolumeFirstField, titrantVolumeSecondField);

        NumberField controlVolumeField = new NumberField("Объём контроля");
        controlVolumeField.setSuffixComponent(new Span("см³"));
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

        massNaveskiFirstField.addValueChangeListener(e -> updateCalculatedFields());
        massNaveskiSecondField.addValueChangeListener(e -> updateCalculatedFields());
        titrantVolumeFirstField.addValueChangeListener(e -> updateCalculatedFields());
        titrantVolumeSecondField.addValueChangeListener(e -> updateCalculatedFields());
        controlVolumeField.addValueChangeListener(e -> updateCalculatedFields());
        coefficientField.addValueChangeListener(e -> updateCalculatedFields());

        return form;
    }

    @Override
    public void setResearch(ProteinsResearch research) {
        emptyResearchForm.setResearch(research);
    }

    @Override
    public Optional<ProteinsResearch> getResearch() {
        return emptyResearchForm.getResearch();
    }

    @Override
    public void updateCalculatedFields() {
        emptyResearchForm.updateCalculatedFields();
    }
}