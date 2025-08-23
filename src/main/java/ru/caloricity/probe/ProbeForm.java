package ru.caloricity.probe;

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
import ru.caloricity.common.Updater;

import java.util.Optional;
import java.util.function.Consumer;

public class ProbeForm extends FormLayout {
    private Probe entity;
    private final Binder<Probe> binder;
    private final NumberField massFactField;
    private final NumberField mineralsField;
    private final Updater updater;

    public ProbeForm(boolean isEdit, Updater updater, Consumer<AbstractField.ComponentValueChangeEvent<Select<ProbeType>, ProbeType>> probeTypeValueChangeListener) {
        this.updater = updater;
        binder = new Binder<>(Probe.class);

        TextField codeField = new TextField("Код пробы");
        binder.forField(codeField)
            .asRequired("Обязательное поле")
            .withValidator(new StringLengthValidator(
                "Должно быть от 2 до 30 символов", 2, 30))
            .bind(Probe::getCode, Probe::setCode);
        add(codeField);

        TextField nameField = new TextField("Наименование пробы");
        binder.forField(nameField)
            .asRequired("Обязательное поле")
            .withValidator(new StringLengthValidator(
                "Должно быть от 2 до 30 символов", 2, 30))
            .bind(Probe::getName, Probe::setName);
        add(nameField);

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
                "Должно быть положительным", 0.1, Double.MAX_VALUE))
            .bind(Probe::getBankaWithProbeMass, Probe::setBankaWithProbeMass);
        add(filledBankaField);
        add(emptyBankaField);

        massFactField = new NumberField("Масса фактическая");
        massFactField.setSuffixComponent(new Span("г"));
        massFactField.setReadOnly(true);
        binder.forField(massFactField)
            .bind(Probe::getMassFact, null);
        add(massFactField);

        mineralsField = new NumberField("Минеральные вещества");
        mineralsField.setSuffixComponent(new Span("г"));
        mineralsField.setReadOnly(true);
        binder.forField(mineralsField)
            .bind(Probe::getMinerals, null);
        add(mineralsField);

        typeSelect.addValueChangeListener(e -> updateCalculatedFields());
        massTheoryField.addValueChangeListener(e -> updateCalculatedFields());
        emptyBankaField.addValueChangeListener(e -> updateCalculatedFields());
        filledBankaField.addValueChangeListener(e -> updateCalculatedFields());
    }

    private void updateCalculatedFields() {
        binder.writeBeanAsDraft(entity);
        massFactField.setValue(entity.getMassFact());
        mineralsField.setValue(entity.getMinerals());
        updater.trigger();
    }

    public void setEntity(Probe probe) {
        this.entity = probe;
        binder.readBean(probe);
    }

    public Optional<Probe> getEntity() {
        return Optional.ofNullable(entity)
            .filter(binder::writeBeanIfValid);
    }
}
