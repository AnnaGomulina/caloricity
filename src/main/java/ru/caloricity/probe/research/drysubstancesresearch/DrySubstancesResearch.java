package ru.caloricity.probe.research.drysubstancesresearch;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.AnyNull;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.common.FourDigitsFormat;
import ru.caloricity.probe.Probe;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Comment("Исследования на сухие вещества")
@Table(name = "dry_substances_researches")
@Builder
public class DrySubstancesResearch extends BaseEntity {
    @Comment("Масса бюксы первая параллель, г")
    @NotNull
    private Double byuksaParallelFirst;

    @Comment("Масса бюксы вторая параллель, г")
    @NotNull
    private Double byuksaParallelSecond;

    @Comment("Масса навески первая параллель, г")
    @NotNull
    private Double massNaveskiParallelFirst;

    @Comment("Масса навески вторая параллель, г")
    @NotNull
    private Double massNaveskiParallelSecond;

    @Comment("Масса бюксы с пробой после высушивания первая параллель, г")
    @NotNull
    private Double byuksaAfterDryingParallelFirst;

    @Comment("Масса бюксы с пробой после высушивания вторая параллель, г")
    @NotNull
    private Double byuksaAfterDryingParallelSecond;

    @OneToOne(mappedBy = "drySubstancesResearch")
    private Probe probe;

    public Double byuksaBeforeDryingParallelFirst() {
        if (new AnyNull(byuksaParallelFirst, massNaveskiParallelFirst).is()) {
            return null;
        }
        return new FourDigitsFormat(byuksaParallelFirst + massNaveskiParallelFirst).it();
    }

    public Double byuksaBeforeDryingParallelSecond() {
        if (new AnyNull(byuksaParallelSecond, massNaveskiParallelSecond).is()) {
            return null;
        }
        return new FourDigitsFormat(byuksaParallelSecond + massNaveskiParallelSecond).it();
    }

    public Double getDrySubstancesParallelFirst() {
        if (new AnyNull(byuksaAfterDryingParallelFirst, byuksaParallelFirst, massNaveskiParallelFirst, probe.getMassFact()).is()) {
            return null;
        }
        double c = (byuksaAfterDryingParallelFirst - byuksaParallelFirst) / massNaveskiParallelFirst * probe.getMassFact();
        return new FourDigitsFormat(c).it();
    }

    public Double getDrySubstancesParallelSecond() {
        if (new AnyNull(byuksaAfterDryingParallelSecond, byuksaParallelSecond, massNaveskiParallelSecond, probe.getMassFact()).is()) {
            return null;
        }
        double c =  (byuksaAfterDryingParallelSecond - byuksaParallelSecond) / massNaveskiParallelSecond * probe.getMassFact();
        return new FourDigitsFormat(c).it();
    }

    /**
     * Сухие вещества фактические на пробу
     */
    public Double getDrySubstancesAverage() {
        if (new AnyNull(getDrySubstancesParallelFirst(), getDrySubstancesParallelSecond()).is()) {
            return null;
        }
        double c = (getDrySubstancesParallelFirst() + getDrySubstancesParallelSecond()) / 2.0;
        return new FourDigitsFormat(c).it();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> objectEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != objectEffectiveClass) return false;
        DrySubstancesResearch that = (DrySubstancesResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
