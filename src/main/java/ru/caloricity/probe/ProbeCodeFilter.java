package ru.caloricity.probe;

import com.vaadin.flow.component.textfield.TextField;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProbeCodeFilter extends TextField implements Specification<Probe> {

    public ProbeCodeFilter() {
        super("Поиск по коду");
        setPlaceholder("Код пробы");
    }

    @NotNull
    @Override
    public Predicate toPredicate(@NotNull Root<Probe> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!getValue().isEmpty()) {
            String lowerCaseFilter = getValue().toLowerCase();
            Predicate name = criteriaBuilder.like(criteriaBuilder.lower(root.get("code")),
                lowerCaseFilter + "%");
            predicates.add(criteriaBuilder.or(name));
        }
        query.orderBy(criteriaBuilder.desc(root.get("updatedAt")));
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
