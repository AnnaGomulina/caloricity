package ru.caloricity.probe;

import com.vaadin.flow.component.html.Span;

import java.util.Map;

public class ProbeTypeChip extends Span {
    private static final Map<ProbeType, String> toRussianMap = Map.of(
        ProbeType.FIRST, "Первое",
        ProbeType.SECOND, "Второе",
        ProbeType.THIRD, "Третье"
    );
    private static final Map<ProbeType, String> toColourMap = Map.of(
        ProbeType.FIRST, "success",
        ProbeType.SECOND, "primary",
        ProbeType.THIRD, "warning"
    );

    public ProbeTypeChip(ProbeType probeType) {
        super(toRussianMap.get(probeType));
        getElement().getThemeList().add("badge " + toColourMap.get(probeType));
    }
}
