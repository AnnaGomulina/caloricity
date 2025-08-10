package com.example.demo.probe.research.fatsresearch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FatsResearchRepository extends JpaRepository<FatsResearch, UUID> {
}