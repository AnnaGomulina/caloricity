package ru.caloricity.probe.research;

import java.util.Optional;

public interface ResearchForm<T> {
    void setResearch(T research);

    Optional<T> getResearch();
}
