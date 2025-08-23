package ru.caloricity.probe.research;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.NumberField;
import ru.caloricity.common.Updater;

public class ResultCard {
    private final Updater updater;

    public ResultCard(Updater updater) {
        this.updater = updater;
    }

    public Component component() {
        FormLayout form = new FormLayout();
        form.setAutoResponsive(true);
        form.setExpandFields(true);
        form.setExpandColumns(true);

        NumberField drySubstancesFact = new NumberField("Сухие вещества фактические");
        drySubstancesFact.setSuffixComponent(new Span("г"));
        drySubstancesFact.setReadOnly(true);
        NumberField drySubstancesTheory = new NumberField("Сухие вещества теоретически");
        drySubstancesTheory.setSuffixComponent(new Span("г"));
        drySubstancesTheory.setReadOnly(true);
        NumberField drySubstancesDifference = new NumberField("Сухие вещества отклонение");
        drySubstancesDifference.setSuffixComponent(new Span("%"));
        drySubstancesDifference.setReadOnly(true);
        form.addFormRow(drySubstancesFact, drySubstancesTheory, drySubstancesDifference);

        NumberField fatsFact = new NumberField("Жиры фактические");
        fatsFact.setSuffixComponent(new Span("г"));
        fatsFact.setReadOnly(true);
        NumberField fatsTheory = new NumberField("Жиры теоретически");
        fatsTheory.setSuffixComponent(new Span("г"));
        fatsTheory.setReadOnly(true);
        NumberField fatsDifference = new NumberField("Жиры отклонение");
        fatsDifference.setSuffixComponent(new Span("%"));
        fatsDifference.setReadOnly(true);
        form.addFormRow(fatsFact, fatsTheory, fatsDifference);

        NumberField proteinsFact = new NumberField("Белки фактические");
        proteinsFact.setSuffixComponent(new Span("г"));
        proteinsFact.setReadOnly(true);
        NumberField proteinsTheory = new NumberField("Белки теоретически");
        proteinsTheory.setSuffixComponent(new Span("г"));
        proteinsTheory.setReadOnly(true);
        NumberField proteinsDifference = new NumberField("Белки отклонение");
        proteinsDifference.setSuffixComponent(new Span("%"));
        proteinsDifference.setReadOnly(true);
        form.addFormRow(proteinsFact, proteinsTheory, proteinsDifference);

        NumberField carbonatesFact = new NumberField("Углеводы фактические");
        carbonatesFact.setSuffixComponent(new Span("г"));
        carbonatesFact.setReadOnly(true);
        NumberField carbonatesTheory = new NumberField("Углеводы теоретически");
        carbonatesTheory.setSuffixComponent(new Span("г"));
        carbonatesTheory.setReadOnly(true);
        NumberField carbonatesDifference = new NumberField("Углеводы отклонение");
        carbonatesDifference.setSuffixComponent(new Span("%"));
        carbonatesDifference.setReadOnly(true);
        form.addFormRow(carbonatesFact, carbonatesTheory, carbonatesDifference);

        NumberField caloricityFact = new NumberField("Калорийность фактическая");
        caloricityFact.setSuffixComponent(new Span("г"));
        caloricityFact.setReadOnly(true);
        NumberField caloricityTheory = new NumberField("Калорийность теоретическая");
        caloricityTheory.setSuffixComponent(new Span("г"));
        caloricityTheory.setReadOnly(true);
        NumberField caloricityDifference = new NumberField("Калорийность отклонение");
        caloricityDifference.setSuffixComponent(new Span("%"));
        caloricityDifference.setReadOnly(true);
        form.addFormRow(caloricityFact, caloricityTheory, caloricityDifference);

        updater.setDrySubstancesFact(drySubstancesFact);
        updater.setDrySubstancesTheory(drySubstancesTheory);
        updater.setDrySubstancesDifference(drySubstancesDifference);
        updater.setFatsFact(fatsFact);
        updater.setFatsTheory(fatsTheory);
        updater.setFatsDifference(fatsDifference);
        updater.setProteinsFact(proteinsFact);
        updater.setProteinsTheory(proteinsTheory);
        updater.setProteinsDifference(proteinsDifference);
        updater.setCarbonatesFact(carbonatesFact);
        updater.setCarbonatesTheory(carbonatesTheory);
        updater.setCarbonatesDifference(carbonatesDifference);
        updater.setCaloricityFact(caloricityFact);
        updater.setCaloricityTheory(caloricityTheory);
        updater.setCaloricityDifference(caloricityDifference);

        Card card = new Card();
        card.setTitle("Результаты");
        card.add(form);
        card.setSizeFull();
        return card;
    }
}
