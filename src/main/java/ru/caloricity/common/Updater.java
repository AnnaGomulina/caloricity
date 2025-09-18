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

    public void setByuksaBeforeDryingParallelFirst(NumberField byuksaBeforeDryingParallelFirst) {
        this.byuksaBeforeDryingParallelFirst = byuksaBeforeDryingParallelFirst;
    }

    public void setByuksaBeforeDryingParallelSecond(NumberField byuksaBeforeDryingParallelSecond) {
        this.byuksaBeforeDryingParallelSecond = byuksaBeforeDryingParallelSecond;
    }

    public void setPatronMassBeforeExtractionParallelFirst(NumberField patronMassBeforeExtractionParallelFirst) {
        this.patronMassBeforeExtractionParallelFirst = patronMassBeforeExtractionParallelFirst;
    }

    public void setPatronMassBeforeExtractionParallelSecond(NumberField patronMassBeforeExtractionParallelSecond) {
        this.patronMassBeforeExtractionParallelSecond = patronMassBeforeExtractionParallelSecond;
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
    private NumberField byuksaBeforeDryingParallelFirst;
    private NumberField byuksaBeforeDryingParallelSecond;
    private NumberField patronMassBeforeExtractionParallelFirst;
    private NumberField patronMassBeforeExtractionParallelSecond;

    public void trigger() {
        if (probe == null) {
            return;
        }
        drySubstancesFact.setValue(Optional.ofNullable(probe.getDrySubstancesResearch()).map(DrySubstancesResearch::getDrySubstancesAverage).orElse(null));
        drySubstancesTheory.setValue(probe.getTheoryDrySubstances());
        drySubstancesDifference.setValue(probe.getDrySubstancesDifference());

        fatsFact.setValue(Optional.ofNullable(probe.getFatsResearch()).map(FatsResearch::getFatsAverage).orElse(null));
        fatsTheory.setValue(probe.getTheoryFats());
        fatsDifference.setValue(probe.getFatsDifference());

        proteinsFact.setValue(Optional.ofNullable(probe.getProteinsResearch()).map(ProteinsResearch::getProteinsAverage).orElse(null));
        proteinsTheory.setValue(probe.getTheoryProteins());
        proteinsDifference.setValue(probe.getProteinsDifference());

        carbonatesFact.setValue(probe.getFactCarbohydrates());
        carbonatesTheory.setValue(probe.getTheoryCarbohydrates());
        carbonatesDifference.setValue(probe.getCarbohydratesDifference());

        caloricityFact.setValue(probe.getFactCaloricity());
        caloricityTheory.setValue(probe.getTheoryCaloricity());
        caloricityDifference.setValue(probe.getCaloricityDifference());

        if (probe.getDrySubstancesResearch() != null) {
            byuksaBeforeDryingParallelFirst.setValue(probe.getDrySubstancesResearch().byuksaBeforeDryingParallelFirst());
            byuksaBeforeDryingParallelSecond.setValue(probe.getDrySubstancesResearch().byuksaBeforeDryingParallelSecond());
        }

        if (probe.getFatsResearch() != null) {
            patronMassBeforeExtractionParallelFirst.setValue(probe.getFatsResearch().patronMassBeforeExtractionParallelFirst());
            patronMassBeforeExtractionParallelSecond.setValue(probe.getFatsResearch().patronMassBeforeExtractionParallelSecond());
        }
    }
}
