package ru.caloricity.probeingredient;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;
import ru.caloricity.common.AnyNull;
import ru.caloricity.common.BaseEntity;
import ru.caloricity.ingredient.Ingredient;
import ru.caloricity.probe.CaloricityCoefficient;
import ru.caloricity.probe.Probe;

import java.util.Objects;

@Entity
@Table(name = "probe_ingredient", indexes = @Index(columnList = "probeId, ingredientId", unique = true))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProbeIngredient extends BaseEntity {
    @Comment("Масса брутто, г")
    @Nullable
    private Double gross;

    @Comment("Масса нетто, г")
    @NotNull
    private Double net;

    @ManyToOne(optional = false)
    @NotNull
    private Probe probe;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private Ingredient ingredient;

    public Double drySubstances() {
        return net - (net * ingredient.getWater()) / 100;
    }

    public Double proteins() {
        return net * ingredient.getProteins() / 100;
    }

    public Double fats() {
        return net * ingredient.getFats() / 100;
    }

    public Double carbohydrates() {
        return net * ingredient.getCarbohydrates() / 100;
    }

    public Double drySubstancesForProbe() {
        if (new AnyNull(probe.massCoefficient(), probe.getType()).is()) {
            return null;
        }
        return drySubstances() * probe.getType().coefficientOfLossesForDrySubstances * probe.massCoefficient();
    }

    public Double proteinsForProbe() {
        if (new AnyNull(probe.massCoefficient()).is()) {
            return null;
        }
        return proteins() * ingredient.getType().coefficientOfLossesForProtein * probe.massCoefficient();
    }

    public Double fatsForProbe() {
        if (probe.massCoefficient() == null) {
            return null;
        }
        return fats() * ingredient.getType().coefficientOfLossesForFat * probe.massCoefficient();
    }

    public Double carbohydratesForProbe() {
        if (probe.massCoefficient() == null) {
            return null;
        }
        return carbohydrates() * ingredient.getType().coefficientOfLossesForCarbohydrates * probe.massCoefficient();
    }

    public Double caloricityForProbe() {
        if (probe.massCoefficient() == null) {
            return null;
        }
        return proteinsForProbe() * CaloricityCoefficient.PROTEINS
               + fatsForProbe() * CaloricityCoefficient.FATS
               + carbohydratesForProbe() * CaloricityCoefficient.CARBOHYDRATES;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> objectEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != objectEffectiveClass) return false;
        ProbeIngredient that = (ProbeIngredient) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
