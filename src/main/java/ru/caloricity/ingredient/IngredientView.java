package ru.caloricity.ingredient;

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

@PageTitle("Ингредиенты")
@Route("ingredients")
@Menu(order = 1, icon = LineAwesomeIconUrl.CARROT_SOLID)
public class IngredientView extends Div {

    public IngredientView(IngredientService ingredientService) {
        IngredientGrid grid = new IngredientGrid();
        IngredientNameFilter filter = new IngredientNameFilter();
        setSizeFull();

        Button addButton = new Button("Добавить ингредиент", event -> {
            new IngredientDialog(ingredient -> {
                var saved = ingredientService.save(ingredient);
                grid.getDataProvider().refreshAll();
                grid.select(saved);
            }).open();
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button searchBtn = new Button("Поиск");
        searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchBtn.addClickListener(e -> grid.getDataProvider().refreshAll());
        searchBtn.addClickShortcut(Key.ENTER);

        HorizontalLayout actions = new HorizontalLayout(filter, searchBtn, addButton);
        actions.setAlignItems(FlexComponent.Alignment.BASELINE);
        grid.setItems(query -> ingredientService.list(VaadinSpringDataHelpers.toSpringPageRequest(query), filter).stream());

        VerticalLayout layout = new VerticalLayout(actions, grid);
        layout.setSizeFull();
        add(layout);
    }
}
