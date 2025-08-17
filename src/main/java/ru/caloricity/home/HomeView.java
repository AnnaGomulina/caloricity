package ru.caloricity.home;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Caloricity")
public class HomeView extends VerticalLayout {

    public HomeView() {
        Image img = new Image("images/home.jpeg", "home.jpeg");
        add(img);
        setAlignItems(Alignment.CENTER);
    }
}
