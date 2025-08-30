package ru.caloricity.probe;

import com.vaadin.flow.component.html.Span;

import java.util.Map;

public class ProbeTypeChip extends Span {
    private static final Map<ProbeType, String> toColourMap = Map.of(
        ProbeType.FIRST, "success",
        ProbeType.SECOND, "primary",
        ProbeType.THIRD, "warning"
    );

    public ProbeTypeChip(ProbeType probeType) {
        super(probeType.russianName);
        getElement().getThemeList().add("badge " + toColourMap.get(probeType));
    }
}
