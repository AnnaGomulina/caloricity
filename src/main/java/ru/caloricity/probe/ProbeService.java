package ru.caloricity.probe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProbeService {
    private final ProbeRepository repository;

    public Optional<Probe> findById(Integer id) {
        return repository.findById(id);
    }

    public Page<Probe> list(Pageable springPageRequest, Specification<Probe> filters) {
        return repository.findAll(filters, springPageRequest);
    }

    public void save(Probe probe) {
        repository.save(probe);
    }

    public void delete(Probe probe) {
        repository.delete(probe);
    }
}
