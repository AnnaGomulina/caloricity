package ru.caloricity.ingredient;

import com.vaadin.flow.component.Composite;
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

public class IngredientForm extends Composite<FormLayout> {
    private Ingredient formDataObject;
    private final Binder<Ingredient> binder;

    public IngredientForm() {
        var formLayout = getContent();
        binder = new Binder<>(Ingredient.class);
        TextField nameField = new TextField("Наименование ингредиента");
        formLayout.add(nameField);
        binder.forField(nameField)
            .asRequired("Обязательное поле")
            .withValidator(new StringLengthValidator("Длина названия должна быть между 2 и 30 символами", 2, 30))
            .bind(Ingredient::getName, Ingredient::setName);

        NumberField ediblePartField = new NumberField("Съедобная часть");
        ediblePartField.setSuffixComponent(new Span("доля"));
        binder.forField(ediblePartField)
            .asRequired("Обязательное поле")
            .withValidator(new DoubleRangeValidator("От 0 до 1", 0., 1.))
            .bind(Ingredient::getEdiblePart, Ingredient::setEdiblePart);
        formLayout.add(ediblePartField);

        NumberField waterField = new NumberField("Масса воды");
        waterField.setSuffixComponent(new Span("г"));
        binder.forField(waterField)
            .asRequired("Обязательное поле")
            .bind(Ingredient::getWater, Ingredient::setWater);
        formLayout.add(waterField);

        NumberField proteinsField = new NumberField("Масса белков");
        proteinsField.setSuffixComponent(new Span("г"));
        binder.forField(proteinsField)
            .asRequired("Обязательное поле")
            .bind(Ingredient::getProteins, Ingredient::setProteins);
        formLayout.add(proteinsField);

        NumberField fatsField = new NumberField("Масса жиров");
        fatsField.setSuffixComponent(new Span("г"));
        binder.forField(fatsField)
            .asRequired("Обязательное поле")
            .bind(Ingredient::getFats, Ingredient::setFats);
        formLayout.add(fatsField);

        NumberField carbsField = new NumberField("Масса углеводов");
        carbsField.setSuffixComponent(new Span("г"));
        binder.forField(carbsField)
            .asRequired("Обязательное поле")
            .bind(Ingredient::getCarbohydrates, Ingredient::setCarbohydrates);
        formLayout.add(carbsField);

        Select<IngredientType> typeSelect = new Select<>();
        typeSelect.setLabel("Тип ингредиента");
        typeSelect.setItems(IngredientType.values());
        typeSelect.setRenderer(new ComponentRenderer<>(IngredientTypeChip::new));
        binder.forField(typeSelect)
            .asRequired("Обязательное поле")
            .bind(Ingredient::getType, Ingredient::setType);
        formLayout.add(typeSelect);
    }

    public void setFormDataObject(Ingredient ingredient) {
        this.formDataObject = ingredient;
        binder.readBean(ingredient);
    }

    public Optional<Ingredient> get() {
        return Optional.ofNullable(formDataObject)
            .filter(binder::writeBeanIfValid);
    }
}
