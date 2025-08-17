package ru.caloricity.common;

import ru.caloricity.probe.Probe;
import com.vaadin.flow.component.textfield.NumberField;

public class Updater {
    public void setProbe(Probe probe) {
        this.probe = probe;
    }

    private Probe probe;

    public void setTheoryCaloricityField(NumberField theoryCaloricityField) {
        this.theoryCaloricityField = theoryCaloricityField;
    }

    private NumberField theoryCaloricityField;

    public void trigger() {
        if (probe == null) {
            return;
        }
        theoryCaloricityField.setValue(probe.getTheoryCaloricity());
    }
}
