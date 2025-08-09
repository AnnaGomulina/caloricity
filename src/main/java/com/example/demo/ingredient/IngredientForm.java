package com.example.demo.ingredient;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;

import java.util.Optional;

public class IngredientForm extends Composite<FormLayout> {
    private Ingredient formDataObject;
    private final Binder<Ingredient> binder;

    public IngredientForm() {
        var formLayout = getContent();
        binder = new Binder<>();
        TextField nameField = new TextField("Наименование ингредиента");
        formLayout.add(nameField);
        binder.forField(nameField)
            .asRequired()
            .withValidator(new StringLengthValidator("Длина названия должна быть между 2 и 30 символами", 2, 30))
            .bind(Ingredient::getName, Ingredient::setName);

        NumberField ediblePartField = new NumberField("Съедобная часть, доля");
        binder.forField(ediblePartField)
            .asRequired()
            .withValidator(new DoubleRangeValidator("От 0 до 1", 0., 1.))
            .bind(Ingredient::getEdiblePart, Ingredient::setEdiblePart);
        formLayout.add(ediblePartField);

        NumberField waterField = new NumberField("Масса воды, г");
        binder.forField(waterField)
            .asRequired()
            .bind(Ingredient::getWater, Ingredient::setWater);
        formLayout.add(waterField);

        NumberField proteinsField = new NumberField("Масса белков, г");
        binder.forField(proteinsField)
            .asRequired()
            .bind(Ingredient::getProteins, Ingredient::setProteins);
        formLayout.add(proteinsField);

        NumberField fatsField = new NumberField("Масса жиров, г");
        binder.forField(fatsField)
            .asRequired()
            .bind(Ingredient::getFats, Ingredient::setFats);
        formLayout.add(fatsField);

        NumberField carbsField = new NumberField("Масса углеводов, г");
        binder.forField(carbsField)
            .asRequired()
            .bind(Ingredient::getCarbohydrates, Ingredient::setCarbohydrates);
        formLayout.add(carbsField);
    }

    public Optional<Ingredient> getFormDataObject() {
        if (formDataObject == null) {
            formDataObject = new Ingredient();
        }
        if (binder.writeBeanIfValid(formDataObject)) {
            return Optional.of(formDataObject);
        } else {
            return Optional.empty();
        }
    }
}
