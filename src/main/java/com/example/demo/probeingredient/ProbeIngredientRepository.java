package com.example.demo.probeingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProbeIngredientRepository extends JpaRepository<ProbeIngredient, UUID> {
}