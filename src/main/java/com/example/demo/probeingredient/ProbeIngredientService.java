package com.example.demo.probeingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProbeIngredientService {
    private final ProbeIngredientRepository repository;

    public void delete(ProbeIngredient probeIngredient) {
        repository.delete(probeIngredient);
    }
}
