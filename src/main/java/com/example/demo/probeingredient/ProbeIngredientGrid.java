package com.example.demo.probeingredient;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.text.DecimalFormat;
import java.util.function.Consumer;

public class ProbeIngredientGrid extends Grid<ProbeIngredient> {
    private Consumer<ProbeIngredient> deleteHandler;

    public ProbeIngredientGrid() {
        super(ProbeIngredient.class, false);
        initColumns();
        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
    }

    public void setDeleteHandler(Consumer<ProbeIngredient> deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    private void initColumns() {
        addColumn(probeIngredient -> probeIngredient.getIngredient().getName())
            .setHeader("Ингредиент")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::getGross)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::getGross, new DecimalFormat("#.####")))
            .setHeader("Масса брутто (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::getNet)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::getNet, new DecimalFormat("#.####")))
            .setHeader("Масса нетто (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::drySubstances)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::drySubstances, new DecimalFormat("#.####")))
            .setHeader("Сухие вещества (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::proteins)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::proteins, new DecimalFormat("#.####")))
            .setHeader("Белки (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::fats)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::fats, new DecimalFormat("#.####")))
            .setHeader("Жиры (г)")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(ProbeIngredient::carbohydrates)
            .setRenderer(new NumberRenderer<>(ProbeIngredient::carbohydrates, new DecimalFormat("#.####")))
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
}