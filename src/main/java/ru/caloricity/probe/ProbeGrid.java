package ru.caloricity.probe;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.RouteParameters;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.function.Consumer;

public class ProbeGrid extends Grid<Probe> {
    private Consumer<Probe> deleteHandler;

    public ProbeGrid() {
        super(Probe.class, false);
        initColumns();
        addThemeVariants(GridVariant.LUMO_NO_BORDER);
        addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
    }

    public void setDeleteHandler(Consumer<Probe> deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    private void initColumns() {
        addColumn(Probe::getCode)
            .setAutoWidth(true)
            .setHeader("Код пробы")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(Probe::getName)
            .setAutoWidth(true)
            .setHeader("Наименование пробы")
            .setTextAlign(ColumnTextAlign.CENTER);

        addColumn(Probe::getType)
            .setAutoWidth(true)
            .setHeader("Тип пробы")
            .setTextAlign(ColumnTextAlign.CENTER)
            .setRenderer(new ComponentRenderer<>(probe -> new ProbeTypeChip(probe.getType())));

        addColumn(Probe::getIsReady)
            .setAutoWidth(true)
            .setHeader("Готовность пробы")
            .setTextAlign(ColumnTextAlign.CENTER)
            .setRenderer(new ComponentRenderer<>(probe -> {
                Checkbox checkbox = new Checkbox(probe.getIsReady());
                checkbox.setReadOnly(true);
                return checkbox;
            }));

        addColumn(new ComponentRenderer<>(this::createActionButtons))
            .setHeader("Действия")
            .setAutoWidth(true)
            .setFlexGrow(0)
            .setTextAlign(ColumnTextAlign.CENTER);
    }

    private Component createActionButtons(Probe probe) {
        Button editButton = new Button(LineAwesomeIcon.EDIT.create());
        editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        editButton.setTooltipText("Редактировать");
        editButton.addClickListener(e ->
            getUI().ifPresent(ui ->
                ui.navigate(ProbeEditView.class, new RouteParameters("probeId", probe.getId().toString()))
            )
        );

        Button deleteButton = new Button(LineAwesomeIcon.TRASH_ALT.create());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        deleteButton.setTooltipText("Удалить");
        deleteButton.addClickListener(e -> {
            if (deleteHandler != null) {
                showDeleteConfirmation(probe);
            }
        });

        return new HorizontalLayout(editButton, deleteButton);
    }

    private void showDeleteConfirmation(Probe probe) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Удаление пробы");
        dialog.setText("Вы уверены, что хотите удалить пробу \"" + probe.getCode() + "\"?");
        dialog.setCancelable(true);
        dialog.setConfirmText("Удалить");
        dialog.setConfirmButtonTheme("error primary");
        dialog.setCancelText("Отмена");

        dialog.addConfirmListener(e -> {
            deleteHandler.accept(probe);
        });

        dialog.open();
    }
}
