package com.example.demo.probe;

import com.example.demo.common.BaseEntity;
import com.example.demo.probe.research.drysubstancesresearch.DrySubstancesResearch;
import com.example.demo.probe.research.fatsresearch.FatsResearch;
import com.example.demo.probe.research.proteinsresearch.ProteinsResearch;
import com.example.demo.probeingredient.ProbeIngredient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Comment("Пробы блюд")
@Table(name = "probes")
@Builder
@AllArgsConstructor
public class Probe extends BaseEntity {
    @Comment("Наименование пробы")
    @NotNull
    private String name;

    @Comment("Тип пробы")
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProbeType type;

    @Comment("Код пробы")
    @NotNull
    private String code;

    @Comment("Масса теоретическая, г")
    @NotNull
    private Double massTheory;

    @Comment("Масса пустой банки, г")
    @NotNull
    private Double bankaEmptyMass;

    @Comment("Масса банки с пробой, г")
    @NotNull
    private Double bankaWithProbeMass;

    @Comment("Готовность пробы")
    @NotNull
    private Boolean isReady = false;

    @OneToMany(mappedBy = "probe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<ProbeIngredient> probeIngredients = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private DrySubstancesResearch drySubstancesResearch;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private FatsResearch fatsResearch;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true)
    private ProteinsResearch proteinsResearch;

    /**
     * @return Масса фактическая, г
     */
    public Double getMassFact() {
        return bankaWithProbeMass - bankaEmptyMass;
    }

    /**
     * @return Минеральные вещества, г
     */
    public Double getMinerals() {
        return getMassFact() * type.coefficientOfMinerals;
    }

//    public Double getTheoreticalCaloricity() {
//        return Optional.ofNullable(probeIngredients)
//                .stream()
//                .flatMap(Collection::stream)
//                .map(e -> e.getIngredient().getTheoreticalCaloricity() / 100 * e.getNet())
//                .mapToDouble(Double::doubleValue)
//                .sum();
//    }
    // калорийность
    // считается как (белки + углеводы )* 4 + жиры * 9
    // теоретическая и фактическая считается, и отклонение

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Probe probe = (Probe) o;
        return getId() != null && Objects.equals(getId(), probe.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
