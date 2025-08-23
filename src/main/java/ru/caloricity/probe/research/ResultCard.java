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
        drySubstancesDifference.setMax(30);
        drySubstancesDifference.setMin(-30);
        drySubstancesDifference.setReadOnly(true);

        NumberField fatsFact = new NumberField("Жиры фактические");
        fatsFact.setSuffixComponent(new Span("г"));
        fatsFact.setReadOnly(true);
        NumberField fatsTheory = new NumberField("Жиры теоретически");
        fatsTheory.setSuffixComponent(new Span("г"));
        fatsTheory.setReadOnly(true);
        NumberField fatsDifference = new NumberField("Жиры отклонение");
        fatsDifference.setSuffixComponent(new Span("%"));
        fatsDifference.setMax(5);
        fatsDifference.setMin(-5);
        fatsDifference.setReadOnly(true);

        NumberField proteinsFact = new NumberField("Белки фактические");
        proteinsFact.setSuffixComponent(new Span("г"));
        proteinsFact.setReadOnly(true);
        NumberField proteinsTheory = new NumberField("Белки теоретически");
        proteinsTheory.setSuffixComponent(new Span("г"));
        proteinsTheory.setReadOnly(true);
        NumberField proteinsDifference = new NumberField("Белки отклонение");
        proteinsDifference.setSuffixComponent(new Span("%"));
        proteinsDifference.setMax(5);
        proteinsDifference.setMin(-5);
        proteinsDifference.setReadOnly(true);

        NumberField carbonatesFact = new NumberField("Углеводы фактические");
        carbonatesFact.setSuffixComponent(new Span("г"));
        carbonatesFact.setReadOnly(true);
        NumberField carbonatesTheory = new NumberField("Углеводы теоретически");
        carbonatesTheory.setSuffixComponent(new Span("г"));
        carbonatesTheory.setReadOnly(true);
        NumberField carbonatesDifference = new NumberField("Углеводы отклонение");
        carbonatesDifference.setSuffixComponent(new Span("%"));
        carbonatesDifference.setMax(5);
        carbonatesDifference.setMin(-5);
        carbonatesDifference.setReadOnly(true);

        NumberField caloricityFact = new NumberField("Калорийность фактическая");
        caloricityFact.setSuffixComponent(new Span("ккал"));
        caloricityFact.setReadOnly(true);
        NumberField caloricityTheory = new NumberField("Калорийность теоретическая");
        caloricityTheory.setSuffixComponent(new Span("ккал"));
        caloricityTheory.setReadOnly(true);
        NumberField caloricityDifference = new NumberField("Калорийность отклонение");
        caloricityDifference.setSuffixComponent(new Span("%"));
        caloricityDifference.setMax(5);
        caloricityDifference.setMin(-5);
        caloricityDifference.setReadOnly(true);

        form.addFormRow(drySubstancesFact, fatsFact, proteinsFact, carbonatesFact, caloricityFact);
        form.addFormRow(drySubstancesTheory, fatsTheory, proteinsTheory, carbonatesTheory, caloricityTheory);
        form.addFormRow(drySubstancesDifference, fatsDifference, proteinsDifference, carbonatesDifference, caloricityDifference);

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
