package ru.caloricity.probe.research.fatsresearch;

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
@Comment("Исследования на жиры")
@Table(name = "fats_research")
@Builder
public class FatsResearch extends BaseEntity {
    @Comment("Масса навески первая параллель, г")
    @NotNull
    private Double massNaveskiParallelFirst;

    @Comment("Масса навески вторая параллель, г")
    @NotNull
    private Double massNaveskiParallelSecond;

    @Comment("Масса пустого патрона первая параллель, г")
    @NotNull
    private Double patronMassEmptyParallelFirst;

    @Comment("Масса пустого патрона вторая параллель, г")
    @NotNull
    private Double patronMassEmptyParallelSecond;

    @Comment("Масса патрона после экстракции первая параллель, г")
    @NotNull
    private Double patronMassAfterExtractionParallelFirst;

    @Comment("Масса патрона после экстракции вторая параллель, г")
    @NotNull
    private Double patronMassAfterExtractionParallelSecond;

    @OneToOne(mappedBy = "fatsResearch")
    private Probe probe;

    public Double patronMassBeforeExtractionParallelFirst() {
        if (new AnyNull(patronMassEmptyParallelFirst, massNaveskiParallelFirst).is()) {
            return null;
        }
        return new FourDigitsFormat(patronMassEmptyParallelFirst + massNaveskiParallelFirst).it();
    }

    public Double patronMassBeforeExtractionParallelSecond() {
        if (new AnyNull(patronMassEmptyParallelSecond, massNaveskiParallelSecond).is()) {
            return null;
        }
        return new FourDigitsFormat(patronMassEmptyParallelSecond + massNaveskiParallelSecond).it();
    }

    public Double fatsParallelFirst() {
        if (new AnyNull(patronMassBeforeExtractionParallelFirst(), patronMassAfterExtractionParallelFirst, massNaveskiParallelFirst, probe.massFact()).is()) {
            return null;
        }
        Double massParallelFirst = (patronMassBeforeExtractionParallelFirst() - patronMassAfterExtractionParallelFirst) / massNaveskiParallelFirst * probe.massFact();
        return new FourDigitsFormat(massParallelFirst).it();
    }

    public Double fatsParallelSecond() {
        if (new AnyNull(patronMassBeforeExtractionParallelSecond(), patronMassAfterExtractionParallelSecond, massNaveskiParallelSecond, probe.massFact()).is()) {
            return null;
        }
        Double massParallelSecond = (patronMassBeforeExtractionParallelSecond() - patronMassAfterExtractionParallelSecond) / massNaveskiParallelSecond * probe.massFact();
        return new FourDigitsFormat(massParallelSecond).it();
    }

    public Double fatsAverage() {
        if (new AnyNull(fatsParallelFirst(), fatsParallelSecond()).is()) {
            return null;
        }
        double c = (fatsParallelFirst() + fatsParallelSecond()) / 2.0;
        return new FourDigitsFormat(c).it();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> objectEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != objectEffectiveClass) return false;
        FatsResearch that = (FatsResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
