package ru.caloricity.probe.research.proteinsresearch;

import jakarta.persistence.Entity;
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
import ru.caloricity.probe.Probe;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Comment("Исследования на белок")
@Table(name = "proteins_researches")
@Builder
public class ProteinsResearch extends BaseEntity {
    @Comment("Масса навески первая параллель, г")
    @NotNull
    private Double massNaveskiParallelFirst;

    @Comment("Масса навески вторая параллель, г")
    @NotNull
    private Double massNaveskiParallelSecond;

    @Comment("Объём титранта первая параллель, см^3")
    @NotNull
    private Double titrantVolumeParallelFirst;

    @Comment("Объём титранта вторая параллель, см^3")
    @NotNull
    private Double titrantVolumeParallelSecond;

    @Comment("Объём контроля, г/см^3")
    @NotNull
    private Double controlVolume;

    @Comment("Коэффициент")
    @NotNull
    private Double coefficient;

    @OneToOne(mappedBy = "proteinsResearch")
    private Probe probe;

    public Double proteinsParallelFirst() {
        if (new AnyNull(coefficient, titrantVolumeParallelFirst, controlVolume, massNaveskiParallelFirst, probe.massFact()).is()) {
            return null;
        }
        double c = 0.0014 * coefficient * (titrantVolumeParallelFirst - controlVolume) * 6.25 / massNaveskiParallelFirst * probe.massFact();
        return new FourDigitsFormat(c).it();
    }

    public Double proteinsParallelSecond() {
        if (new AnyNull(coefficient, titrantVolumeParallelSecond, controlVolume, massNaveskiParallelSecond, probe.massFact()).is()) {
            return null;
        }
        double c = 0.0014 * coefficient * (titrantVolumeParallelSecond - controlVolume) * 6.25 / massNaveskiParallelSecond * probe.massFact();
        return new FourDigitsFormat(c).it();
    }

    public Double proteinsAverage() {
        if (new AnyNull(proteinsParallelFirst(), proteinsParallelSecond()).is()) {
            return null;
        }
        double c = (proteinsParallelFirst() + proteinsParallelSecond()) / 2.0;
        return new FourDigitsFormat(c).it();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> objectEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != objectEffectiveClass) return false;
        ProteinsResearch that = (ProteinsResearch) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
