package com.example.demo.probe.research.fatsresearch;

import com.example.demo.common.BaseEntity;
import com.example.demo.probe.Probe;
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

import java.text.DecimalFormat;
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
    @Comment("Масса патрона до экстракции первая параллель, г")
    @NotNull
    private Double patronMassBeforeExtractionParallelFirst;

    @Comment("Масса патрона до экстракции вторая параллель, г")
    @NotNull
    private Double patronMassBeforeExtractionParallelSecond;

    @Comment("Масса патрона после экстракции первая параллель, г")
    @NotNull
    private Double patronMassAfterExtractionParallelFirst;

    @Comment("Масса патрона после экстракции вторая параллель, г")
    @NotNull
    private Double patronMassAfterExtractionParallelSecond;

    @OneToOne(mappedBy = "fatsResearch")
    private Probe probe;

    public Double getMassParallelFirst() {
        if (patronMassBeforeExtractionParallelFirst == null || patronMassAfterExtractionParallelFirst == null) {
            return null;
        }
        Double massParallelFirst = patronMassBeforeExtractionParallelFirst - patronMassAfterExtractionParallelFirst;
        return Double.valueOf(new DecimalFormat("#.####").format(massParallelFirst));
    }

    public Double getMassParallelSecond() {
        if (patronMassBeforeExtractionParallelSecond == null || patronMassAfterExtractionParallelSecond == null) {
            return null;
        }
        Double massParallelSecond = patronMassBeforeExtractionParallelSecond - patronMassAfterExtractionParallelSecond;
        return Double.valueOf(new DecimalFormat("#.####").format(massParallelSecond));
    }

//    public Double getMassForOneGramParallelFirst() {
//        return getMassParallelFirst() / massNaveskiParallelFirst;
//    }
//
//    public Double getMassForOneGramParallelSecond() {
//        return getMassParallelSecond() / massNaveskiParallelSecond;
//    }
//
//    /**
//     * @return Количество жира в одном грамме пробы
//     */
//    public Double getMassForOneGramAverage() {
//        return new Average(getMassForOneGramParallelFirst(), getMassForOneGramParallelSecond()).calc();
//    }

//    public Double getFactCaloricityForOneGram() {
//        return getMassForOneGramAverage() * CaloricityCoefficient.FATS;
//    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        FatsResearch that = (FatsResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
