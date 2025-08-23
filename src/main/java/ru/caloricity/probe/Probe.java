package ru.caloricity.probe;

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
import ru.caloricity.common.AnyNull;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.common.FourDigitsFormat;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;
import ru.caloricity.probeingredient.ProbeIngredient;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
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
    @Builder.Default
    @NotNull
    private Boolean isReady = false;

    @OneToMany(mappedBy = "probe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
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
        if (new AnyNull(bankaWithProbeMass, bankaEmptyMass).is()) {
            return null;
        }
        double c = bankaWithProbeMass - bankaEmptyMass;
        return new FourDigitsFormat(c).it();
    }

    /**
     * @return Коэффициент соотношения массы фактической к теоретической
     */
    public Double getMassCoefficient() {
        if (new AnyNull(getMassFact(), massTheory).is()) {
            return null;
        }
        return getMassFact() / massTheory;
    }

    /**
     * @return Минеральные вещества, г
     */
    public Double getMinerals() {
        if (new AnyNull(getMassFact(), type).is()) {
            return null;
        }
        double c = getMassFact() * type.coefficientOfMinerals;
        return new FourDigitsFormat(c).it();
    }

    public Double getFactCarbohydrates() {
        if (type == ProbeType.THIRD) {
            if (new AnyNull(drySubstancesResearch).is() || new AnyNull(drySubstancesResearch.getDrySubstancesAverage(), getMinerals()).is()) {
                return null;
            }
            double c = drySubstancesResearch.getDrySubstancesAverage() - getMinerals();
            return new FourDigitsFormat(c).it();
        }
        if (new AnyNull(drySubstancesResearch, proteinsResearch).is() || new AnyNull(drySubstancesResearch.getDrySubstancesAverage(), proteinsResearch.getProteinsAverage(), fatsResearch.getFatsAverage(), getMinerals()).is()) {
            return null;
        }
        double c = drySubstancesResearch.getDrySubstancesAverage() - (proteinsResearch.getProteinsAverage() + fatsResearch.getFatsAverage() + getMinerals());
        return new FourDigitsFormat(c).it();
    }

    public Double getTheoryDrySubstances() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::drySubstancesForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double getTheoryFats() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::fatsForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double getTheoryProteins() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::proteinsForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double getTheoryCarbohydrates() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::carbohydratesForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double getTheoryCaloricity() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::caloricityForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double getFactCaloricity() {
        Double proteins = Optional.ofNullable(proteinsResearch).map(ProteinsResearch::getProteinsAverage).orElse(0.);
        Double fats = Optional.ofNullable(fatsResearch).map(FatsResearch::getFatsAverage).orElse(0.);
        Double carbohydrates = Optional.ofNullable(getFactCarbohydrates()).orElse(0.);
        double c = proteins * CaloricityCoefficient.PROTEINS
                   + fats * CaloricityCoefficient.FATS
                   + carbohydrates * CaloricityCoefficient.CARBOHYDRATES;
        return new FourDigitsFormat(c).it();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> objectEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != objectEffectiveClass) return false;
        Probe probe = (Probe) o;
        return getId() != null && Objects.equals(getId(), probe.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
