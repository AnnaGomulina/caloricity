package com.example.demo.probe.research;

import java.util.Optional;

public interface ResearchForm<T> {
    void set(T research);

    Optional<T> get();
}
