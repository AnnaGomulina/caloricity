package ru.caloricity.probeingredient;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ProbeIngredientGrid extends Grid<ProbeIngredient> {
    private Consumer<ProbeIngredient> deleteHandler;

    public ProbeIngredientGrid() {
        super(ProbeIngredient.class, false);
        initColumns();
        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
        setItemDetailsRenderer(createDetailsRenderer());
    }

    public void setDeleteHandler(Consumer<ProbeIngredient> deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    private void initColumns() {
        var df = new DecimalFormat("#.####");
        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        addColumn(probeIngredient -> probeIngredient.getIngredient().getName())
            .setHeader("Ингредиент")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::getGross)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::getGross, df))
            .setHeader("Масса брутто (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::getNet)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::getNet, df))
            .setHeader("Масса нетто (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::drySubstances)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::drySubstances, df))
            .setHeader("Сухие вещества (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::proteins)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::proteins, df))
            .setHeader("Белки (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::fats)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::fats, df))
            .setHeader("Жиры (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::carbohydrates)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::carbohydrates, df))
            .setHeader("Углеводы (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(new ComponentRenderer<>(this::createActionButtons))
            .setHeader("Действия")
            .setTextAlign(ColumnTextAlign.CENTER);
    }

    private Component createActionButtons(ProbeIngredient probeIngredient) {
        Button deleteButton = new Button(LineAwesomeIcon.TRASH_ALT.create());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        deleteButton.setTooltipText("Удалить");
        deleteButton.addClickListener(e -> {
            if (deleteHandler != null) {
                showDeleteConfirmation(probeIngredient);
            }
        });

        return deleteButton;
    }

    private void showDeleteConfirmation(ProbeIngredient probeIngredient) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Удаление ингредиента пробы");
        dialog.setText("Вы уверены, что хотите удалить ингредиент \"" +
                       probeIngredient.getIngredient().getName() + "\" из пробы?");
        dialog.setCancelable(true);
        dialog.setConfirmText("Удалить");
        dialog.setConfirmButtonTheme("error primary");
        dialog.setCancelText("Отмена");

        dialog.addConfirmListener(e -> {
            deleteHandler.accept(probeIngredient);
        });

        dialog.open();
    }

    private static ComponentRenderer<ProbeIngredientFormLayout, ProbeIngredient> createDetailsRenderer() {
        return new ComponentRenderer<>(ProbeIngredientFormLayout::new,
            ProbeIngredientFormLayout::set);
    }

    private static class ProbeIngredientFormLayout extends FormLayout {
        private final NumberField drySubstancesForProbe = new NumberField("Сухие вещества на пробу");
        private final NumberField proteinsForProbe = new NumberField("Белки на пробу");
        private final NumberField fatsForProbe = new NumberField("Жиры на пробу");
        private final NumberField carbohydratesForProbe = new NumberField("Углеводы на пробу");
        private final NumberField caloricityForProbe = new NumberField("Калорийность на пробу");

        public ProbeIngredientFormLayout() {
            Stream.of(drySubstancesForProbe, proteinsForProbe, fatsForProbe, carbohydratesForProbe, caloricityForProbe)
                .forEach(field -> field.setReadOnly(true));
            HorizontalLayout horizontalLayout = new HorizontalLayout(
                new Div("Не пересчитывается автоматически!"),
                drySubstancesForProbe,
                proteinsForProbe,
                fatsForProbe,
                carbohydratesForProbe,
                caloricityForProbe
            );
            horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
            addFormRow(horizontalLayout);
        }

        public void set(ProbeIngredient probeIngredient) {
            drySubstancesForProbe.setValue(probeIngredient.drySubstancesForProbe());
            proteinsForProbe.setValue(probeIngredient.proteinsForProbe());
            fatsForProbe.setValue(probeIngredient.fatsForProbe());
            carbohydratesForProbe.setValue(probeIngredient.carbohydratesForProbe());
            caloricityForProbe.setValue(probeIngredient.caloricityForProbe());
        }
    }
}