package ru.caloricity.common;

import com.vaadin.flow.component.textfield.NumberField;
import ru.caloricity.probe.Probe;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;

import java.util.Optional;

public class Updater {
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
    private NumberField drySubstancesParallelFirst;
    private NumberField drySubstancesParallelSecond;
    private NumberField fatsParallelFirst;
    private NumberField fatsParallelSecond;
    private NumberField proteinsParallelFirst;
    private NumberField proteinsParallelSecond;
    private NumberField afterDryingFirstField;
    private NumberField afterDryingSecondField;
    private NumberField byuksaAfterDryingParallelFirst;
    private NumberField byuksaAfterDryingParallelSecond;
    private NumberField titrantVolumeParallelFirst;
    private NumberField titrantVolumeParallelSecond;

    public void trigger() {
        if (probe == null) {
            return;
        }
        drySubstancesFact.setValue(Optional.ofNullable(probe.getDrySubstancesResearch()).map(DrySubstancesResearch::drySubstancesAverage).orElse(null));
        drySubstancesTheory.setValue(probe.theoryDrySubstances());
        drySubstancesDifference.setValue(probe.drySubstancesDifference());

        fatsFact.setValue(Optional.ofNullable(probe.getFatsResearch()).map(FatsResearch::fatsAverage).orElse(null));
        fatsTheory.setValue(probe.theoryFats());
        fatsDifference.setValue(probe.fatsDifference());

        proteinsFact.setValue(Optional.ofNullable(probe.getProteinsResearch()).map(ProteinsResearch::proteinsAverage).orElse(null));
        proteinsTheory.setValue(probe.theoryProteins());
        proteinsDifference.setValue(probe.proteinsDifference());

        carbonatesFact.setValue(probe.factCarbohydrates());
        carbonatesTheory.setValue(probe.theoryCarbohydrates());
        carbonatesDifference.setValue(probe.carbohydratesDifference());

        caloricityFact.setValue(probe.factCaloricity());
        caloricityTheory.setValue(probe.theoryCaloricity());
        caloricityDifference.setValue(probe.caloricityDifference());

        if (probe.getDrySubstancesResearch() != null) {
            byuksaBeforeDryingParallelFirst.setValue(probe.getDrySubstancesResearch().byuksaBeforeDryingParallelFirst());
            byuksaBeforeDryingParallelSecond.setValue(probe.getDrySubstancesResearch().byuksaBeforeDryingParallelSecond());

            drySubstancesParallelFirst.setValue(probe.getDrySubstancesResearch().drySubstancesParallelFirst());
            drySubstancesParallelSecond.setValue(probe.getDrySubstancesResearch().drySubstancesParallelSecond());

            afterDryingFirstField.setTooltipText(String.valueOf(probe.getDrySubstancesResearch().byuksaAfterDryingParallelFirst(probe.theoryDrySubstances())));
            afterDryingSecondField.setTooltipText(String.valueOf(probe.getDrySubstancesResearch().byuksaAfterDryingParallelSecond(probe.theoryDrySubstances())));
        }

        if (probe.getFatsResearch() != null) {
            patronMassBeforeExtractionParallelFirst.setValue(probe.getFatsResearch().patronMassBeforeExtractionParallelFirst());
            patronMassBeforeExtractionParallelSecond.setValue(probe.getFatsResearch().patronMassBeforeExtractionParallelSecond());

            fatsParallelFirst.setValue(probe.getFatsResearch().fatsParallelFirst());
            fatsParallelSecond.setValue(probe.getFatsResearch().fatsParallelSecond());

            byuksaAfterDryingParallelFirst.setTooltipText(String.valueOf(probe.getFatsResearch().patronMassAfterExtractionParallelFirst(probe.theoryFats())));
            byuksaAfterDryingParallelSecond.setTooltipText(String.valueOf(probe.getFatsResearch().patronMassAfterExtractionParallelSecond(probe.theoryFats())));
        }

        if (probe.getProteinsResearch() != null) {
            proteinsParallelFirst.setValue(probe.getProteinsResearch().proteinsParallelFirst());
            proteinsParallelSecond.setValue(probe.getProteinsResearch().proteinsParallelSecond());

            titrantVolumeParallelFirst.setTooltipText(String.valueOf(probe.getProteinsResearch().titrantVolumeParallelFirst(probe.theoryProteins())));
            titrantVolumeParallelSecond.setTooltipText(String.valueOf(probe.getProteinsResearch().titrantVolumeParallelSecond(probe.theoryProteins())));
        }
    }

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

    public void setDrySubstancesParallelFirst(NumberField drySubstancesParallelFirst) {
        this.drySubstancesParallelFirst = drySubstancesParallelFirst;
    }

    public void setDrySubstancesParallelSecond(NumberField drySubstancesParallelSecond) {
        this.drySubstancesParallelSecond = drySubstancesParallelSecond;
    }

    public void setFatsParallelFirst(NumberField fatsParallelFirst) {
        this.fatsParallelFirst = fatsParallelFirst;
    }

    public void setFatsParallelSecond(NumberField fatsParallelSecond) {
        this.fatsParallelSecond = fatsParallelSecond;
    }

    public void setProteinsParallelFirst(NumberField proteinsParallelFirst) {
        this.proteinsParallelFirst = proteinsParallelFirst;
    }

    public void setProteinsParallelSecond(NumberField proteinsParallelSecond) {
        this.proteinsParallelSecond = proteinsParallelSecond;
    }

    public void setAfterDryingFirstField(NumberField afterDryingFirstField) {
        this.afterDryingFirstField = afterDryingFirstField;
    }

    public void setAfterDryingSecondField(NumberField afterDryingSecondField) {
        this.afterDryingSecondField = afterDryingSecondField;
    }

    public void setByuksaAfterDryingParallelFirst(NumberField byuksaAfterDryingParallelFirst) {
        this.byuksaAfterDryingParallelFirst = byuksaAfterDryingParallelFirst;
    }

    public void setByuksaAfterDryingParallelSecond(NumberField byuksaAfterDryingParallelSecond) {
        this.byuksaAfterDryingParallelSecond = byuksaAfterDryingParallelSecond;
    }

    public void setTitrantVolumeParallelFirst(NumberField titrantVolumeParallelFirst) {
        this.titrantVolumeParallelFirst = titrantVolumeParallelFirst;
    }

    public void setTitrantVolumeParallelSecond(NumberField titrantVolumeParallelSecond) {
        this.titrantVolumeParallelSecond = titrantVolumeParallelSecond;
    }
}
