package ru.caloricity.common;

import com.vaadin.flow.component.textfield.NumberField;
import ru.caloricity.probe.Probe;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;

import java.util.Optional;

public class Updater {
    public void setProbe(Probe probe) {
        this.probe = probe;
    }

    private Probe probe;

    public void setDrySubstancesFact(NumberField drySubstancesFact) {
        this.drySubstancesFact = drySubstancesFact;
    }

    public void setDrySubstancesTheory(NumberField drySubstancesTheory) {
        this.drySubstancesTheory = drySubstancesTheory;
    }

    public void setDrySubstancesDifference(NumberField drySubstancesDifference) {
        this.drySubstancesDifference = drySubstancesDifference;
    }

    public void setFatsFact(NumberField fatsFact) {
        this.fatsFact = fatsFact;
    }

    public void setFatsTheory(NumberField fatsTheory) {
        this.fatsTheory = fatsTheory;
    }

    public void setFatsDifference(NumberField fatsDifference) {
        this.fatsDifference = fatsDifference;
    }

    public void setProteinsFact(NumberField proteinsFact) {
        this.proteinsFact = proteinsFact;
    }

    public void setProteinsTheory(NumberField proteinsTheory) {
        this.proteinsTheory = proteinsTheory;
    }

    public void setProteinsDifference(NumberField proteinsDifference) {
        this.proteinsDifference = proteinsDifference;
    }

    public void setCarbonatesFact(NumberField carbonatesFact) {
        this.carbonatesFact = carbonatesFact;
    }

    public void setCarbonatesTheory(NumberField carbonatesTheory) {
        this.carbonatesTheory = carbonatesTheory;
    }

    public void setCarbonatesDifference(NumberField carbonatesDifference) {
        this.carbonatesDifference = carbonatesDifference;
    }

    public void setCaloricityFact(NumberField caloricityFact) {
        this.caloricityFact = caloricityFact;
    }

    public void setCaloricityTheory(NumberField caloricityTheory) {
        this.caloricityTheory = caloricityTheory;
    }

    public void setCaloricityDifference(NumberField caloricityDifference) {
        this.caloricityDifference = caloricityDifference;
    }

    private NumberField drySubstancesFact;
    private NumberField drySubstancesTheory;
    private NumberField drySubstancesDifference;
    private NumberField fatsFact;
    private NumberField fatsTheory;
    private NumberField fatsDifference;
    private NumberField proteinsFact;
    private NumberField proteinsTheory;
    private NumberField proteinsDifference;
    private NumberField carbonatesFact;
    private NumberField carbonatesTheory;
    private NumberField carbonatesDifference;
    private NumberField caloricityFact;
    private NumberField caloricityTheory;
    private NumberField caloricityDifference;

    public void trigger() {
        if (probe == null) {
            return;
        }
        drySubstancesFact.setValue(Optional.ofNullable(probe.getDrySubstancesResearch()).map(DrySubstancesResearch::getDrySubstancesAverage).orElse(null));
        drySubstancesTheory.setValue(probe.getTheoryDrySubstances());
        drySubstancesDifference.setValue(new Difference(drySubstancesFact.getValue(), drySubstancesTheory.getValue()).get());

        fatsFact.setValue(Optional.ofNullable(probe.getFatsResearch()).map(FatsResearch::getFatsAverage).orElse(null));
        fatsTheory.setValue(probe.getTheoryFats());
        fatsDifference.setValue(new Difference(fatsFact.getValue(), fatsTheory.getValue()).get());

        proteinsFact.setValue(Optional.ofNullable(probe.getProteinsResearch()).map(ProteinsResearch::getProteinsAverage).orElse(null));
        proteinsTheory.setValue(probe.getTheoryProteins());
        proteinsDifference.setValue(new Difference(proteinsFact.getValue(), proteinsTheory.getValue()).get());

        carbonatesFact.setValue(probe.getFactCarbohydrates());
        carbonatesTheory.setValue(probe.getTheoryCarbohydrates());
        carbonatesDifference.setValue(new Difference(carbonatesFact.getValue(), carbonatesTheory.getValue()).get());

        caloricityFact.setValue(probe.getFactCaloricity());
        caloricityTheory.setValue(probe.getTheoryCaloricity());
        caloricityDifference.setValue(new Difference(caloricityFact.getValue(), caloricityTheory.getValue()).get());
    }
}
