package com.example.demo.ingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientRepository repository;

    public Page<Ingredient> list(Pageable springPageRequest, Specification<Ingredient> filters) {
        return repository.findAll(filters, springPageRequest);
    }

    public List<Ingredient> findAll() {
        return repository.findAll();
    }

    public Ingredient save(Ingredient ingredient) {
        return repository.save(ingredient);
    }
}
