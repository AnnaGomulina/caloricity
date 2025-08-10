package com.example.demo.probe;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Пробы")
@Route("probes")
@Menu(order = 2, icon = LineAwesomeIconUrl.FLASK_SOLID)
public class ProbeView extends Div {

    public ProbeView(ProbeService service) {
        ProbeGrid grid = new ProbeGrid();
        ProbeCodeFilter filter = new ProbeCodeFilter();
        setSizeFull();

        Button addButton = new Button("Добавить пробу");
        addButton.addClickListener(e ->
            getUI().ifPresent(ui -> ui.navigate(ProbeCreateView.class))
        );
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button searchBtn = new Button("Поиск");
        searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchBtn.addClickListener(e -> grid.getDataProvider().refreshAll());
        searchBtn.addClickShortcut(Key.ENTER);

        HorizontalLayout actions = new HorizontalLayout(filter, searchBtn, addButton);
        actions.setAlignItems(FlexComponent.Alignment.BASELINE);
        grid.setItems(query -> service.list(VaadinSpringDataHelpers.toSpringPageRequest(query), filter).stream());

        grid.setDeleteHandler(probe -> {
            service.delete(probe);
            grid.getDataProvider().refreshAll();
        });

        VerticalLayout layout = new VerticalLayout(actions, grid);
        layout.setSizeFull();
        add(layout);
    }
}
