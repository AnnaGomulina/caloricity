package com.example.demo.probe.research.proteinsresearch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProteinsResearchRepository extends JpaRepository<ProteinsResearch, UUID> {
}