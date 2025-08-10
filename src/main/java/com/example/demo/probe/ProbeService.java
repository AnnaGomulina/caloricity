package com.example.demo.probe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProbeService {
    private final ProbeRepository repository;

    public Optional<Probe> findById(UUID id) {
        return repository.findById(id);
    }

    public Page<Probe> findAll(Pageable springPageRequest, Specification<Probe> filters) {
        return repository.findAll(filters, springPageRequest);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
