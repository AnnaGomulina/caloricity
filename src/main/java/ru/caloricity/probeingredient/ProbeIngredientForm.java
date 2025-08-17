package ru.caloricity.probeingredient;

import ru.caloricity.ingredient.Ingredient;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;

import java.util.List;
import java.util.Optional;

public class ProbeIngredientForm extends Composite<FormLayout> {
    private ProbeIngredient entity;
    private final Binder<ProbeIngredient> binder;

    public ProbeIngredientForm(List<Ingredient> availableIngredients) {
        var formLayout = getContent();
        binder = new Binder<>(ProbeIngredient.class);

        ComboBox<Ingredient> ingredientCombo = new ComboBox<>("Ингредиент");
        ingredientCombo.setItems(availableIngredients);
        ingredientCombo.setItemLabelGenerator(Ingredient::getName);
        ingredientCombo.setRequired(true);
        binder.forField(ingredientCombo)
            .asRequired("Обязательное поле")
            .bind(ProbeIngredient::getIngredient, ProbeIngredient::setIngredient);
        formLayout.add(ingredientCombo);

        NumberField grossField = new NumberField("Масса брутто");
        grossField.setSuffixComponent(new Span("г"));
        binder.forField(grossField)
            .withValidator(new DoubleRangeValidator("Масса должна быть положительной", 0., null))
            .bind(ProbeIngredient::getGross, ProbeIngredient::setGross);
        formLayout.add(grossField);

        NumberField netField = new NumberField("Масса нетто");
        netField.setSuffixComponent(new Span("г"));
        binder.forField(netField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator("Масса должна быть положительной", 0., null))
            .bind(ProbeIngredient::getNet, ProbeIngredient::setNet);
        formLayout.add(netField);

        grossField.addValueChangeListener(e -> {
                if (e.getValue() != null) {
                    binder.writeBeanAsDraft(entity, true);
                    Optional.ofNullable(entity.getIngredient())
                        .ifPresent(
                            ingredient ->
                                netField.setValue(ingredient.getEdiblePart() * e.getValue())
                        );
                }
            }
        );
    }

    public void set(ProbeIngredient probeIngredient) {
        this.entity = probeIngredient;
        binder.readBean(probeIngredient);
    }

    public Optional<ProbeIngredient> get() {
        return Optional.of(entity)
            .filter(binder::writeBeanIfValid);
    }
}
