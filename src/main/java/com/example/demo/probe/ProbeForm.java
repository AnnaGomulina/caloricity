package com.example.demo.probe;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.util.Optional;
import java.util.function.Consumer;

public class ProbeForm extends FormLayout {
    private Probe formDataObject;
    private final Binder<Probe> binder;

    public ProbeForm(boolean isEdit, Consumer<AbstractField.ComponentValueChangeEvent<Select<ProbeType>, ProbeType>> probeTypeValueChangeListener) {
        binder = new Binder<>(Probe.class);

        // Поле наименования
        TextField nameField = new TextField("Наименование пробы");
        binder.forField(nameField)
            .asRequired("Обязательное поле")
            .withValidator(new StringLengthValidator(
                "Должно быть от 2 до 30 символов", 2, 30))
            .bind(Probe::getName, Probe::setName);
        add(nameField);

        // Поле кода
        TextField codeField = new TextField("Код пробы");
        binder.forField(codeField)
            .asRequired("Обязательное поле")
            .withValidator(new StringLengthValidator(
                "Должно быть от 2 до 30 символов", 2, 30))
            .bind(Probe::getCode, Probe::setCode);
        add(codeField);

        // Выбор типа пробы
        Select<ProbeType> typeSelect = new Select<>();
        typeSelect.setLabel("Тип пробы");
        typeSelect.setReadOnly(isEdit);
        typeSelect.addValueChangeListener(probeTypeValueChangeListener::accept);
        typeSelect.setItems(ProbeType.values());
        typeSelect.setRenderer(new ComponentRenderer<>(ProbeTypeChip::new));
        binder.forField(typeSelect)
            .asRequired("Обязательное поле")
            .bind(Probe::getType, Probe::setType);
        add(typeSelect);

        // Поля массы
        NumberField massTheoryField = new NumberField("Теоретическая масса");
        massTheoryField.setSuffixComponent(new Span("г"));
        binder.forField(massTheoryField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0.1, Double.MAX_VALUE))
            .bind(Probe::getMassTheory, Probe::setMassTheory);
        add(massTheoryField);

        NumberField emptyBankaField = new NumberField("Масса пустой банки");
        emptyBankaField.setSuffixComponent(new Span("г"));
        binder.forField(emptyBankaField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть положительным", 0.1, Double.MAX_VALUE))
            .bind(Probe::getBankaEmptyMass, Probe::setBankaEmptyMass);

        NumberField filledBankaField = new NumberField("Масса банки с пробой");
        filledBankaField.setSuffixComponent(new Span("г"));
        binder.forField(filledBankaField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator(
                "Должно быть больше массы пустой банки", 0.1, Double.MAX_VALUE))
            .withValidator(value -> value > emptyBankaField.getValue(),
                "Должно быть больше массы пустой банки")
            .bind(Probe::getBankaWithProbeMass, Probe::setBankaWithProbeMass);
        add(filledBankaField);
        add(emptyBankaField);
    }

    public void setFormDataObject(Probe probe) {
        this.formDataObject = probe;
        binder.readBean(probe);
    }

    public Optional<Probe> getFormDataObject() {
        return Optional.ofNullable(formDataObject)
            .filter(binder::writeBeanIfValid);
    }
}
