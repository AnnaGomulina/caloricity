package ru.caloricity.probe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import ru.caloricity.common.LocalDateConverter;
import ru.caloricity.common.AnyNull;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.common.Difference;
import ru.caloricity.common.FourDigitsFormat;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;
import ru.caloricity.probeingredient.ProbeIngredient;

import java.time.LocalDate;
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

    @Comment("Дата начала исследования")
    @Convert(converter = LocalDateConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    protected LocalDate startDate;

    @Comment("Дата окончания исследования")
    @Convert(converter = LocalDateConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    protected LocalDate endDate;

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
    public Double massFact() {
        if (new AnyNull(bankaWithProbeMass, bankaEmptyMass).is()) {
            return null;
        }
        double c = bankaWithProbeMass - bankaEmptyMass;
        return new FourDigitsFormat(c).it();
    }

    /**
     * @return Коэффициент соотношения массы фактической к теоретической
     */
    public Double massCoefficient() {
        if (new AnyNull(massFact(), massTheory).is()) {
            return null;
        }
        return massFact() / massTheory;
    }

    /**
     * @return Минеральные вещества, г
     */
    public Double minerals() {
        if (new AnyNull(massFact(), type).is()) {
            return null;
        }
        double c = massFact() * type.coefficientOfMinerals;
        return new FourDigitsFormat(c).it();
    }

    public Double factCarbohydrates() {
        if (type == ProbeType.THIRD) {
            if (new AnyNull(drySubstancesResearch).is() || new AnyNull(drySubstancesResearch.drySubstancesAverage(), minerals()).is()) {
                return null;
            }
            double c = drySubstancesResearch.drySubstancesAverage() - minerals();
            return new FourDigitsFormat(c).it();
        }
        if (new AnyNull(drySubstancesResearch, proteinsResearch).is() || new AnyNull(drySubstancesResearch.drySubstancesAverage(), proteinsResearch.proteinsAverage(), fatsResearch.fatsAverage(), minerals()).is()) {
            return null;
        }
        double c = drySubstancesResearch.drySubstancesAverage() - (proteinsResearch.proteinsAverage() + fatsResearch.fatsAverage() + minerals());
        return new FourDigitsFormat(c).it();
    }

    public Double theoryDrySubstances() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::drySubstancesForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double theoryFats() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::fatsForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double theoryProteins() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::proteinsForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double theoryCarbohydrates() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::carbohydratesForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double theoryCaloricity() {
        double c = probeIngredients.stream()
            .map(ProbeIngredient::caloricityForProbe)
            .filter(Objects::nonNull)
            .reduce(0.0, Double::sum);
        return new FourDigitsFormat(c).it();
    }

    public Double factCaloricity() {
        Double proteins = Optional.ofNullable(proteinsResearch).map(ProteinsResearch::proteinsAverage).orElse(0.);
        Double fats = Optional.ofNullable(fatsResearch).map(FatsResearch::fatsAverage).orElse(0.);
        Double carbohydrates = Optional.ofNullable(factCarbohydrates()).orElse(0.);
        double c = proteins * CaloricityCoefficient.PROTEINS
                   + fats * CaloricityCoefficient.FATS
                   + carbohydrates * CaloricityCoefficient.CARBOHYDRATES;
        return new FourDigitsFormat(c).it();
    }

    public Double drySubstancesDifference() {
        if (new AnyNull(drySubstancesResearch, theoryDrySubstances()).is()) {
            return null;
        }
        return new Difference(drySubstancesResearch.drySubstancesAverage(), theoryDrySubstances()).get();
    }

    public Double fatsDifference() {
        if (new AnyNull(fatsResearch, theoryFats()).is()) {
            return null;
        }
        return new Difference(fatsResearch.fatsAverage(), theoryFats()).get();
    }

    public Double proteinsDifference() {
        if (new AnyNull(proteinsResearch, theoryProteins()).is()) {
            return null;
        }
        return new Difference(proteinsResearch.proteinsAverage(), theoryProteins()).get();
    }

    public Double carbohydratesDifference() {
        if (new AnyNull(factCarbohydrates(), theoryCarbohydrates()).is()) {
            return null;
        }
        return new Difference(factCarbohydrates(), theoryCarbohydrates()).get();
    }

    public Double caloricityDifference() {
        if (new AnyNull(factCaloricity(), theoryCaloricity()).is()) {
            return null;
        }
        return new Difference(factCaloricity(), theoryCaloricity()).get();
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
