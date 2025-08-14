package com.example.demo.probe;

import com.example.demo.common.CancelButton;
import com.example.demo.common.SaveButton;
import com.example.demo.ingredient.IngredientService;
import com.example.demo.probe.research.drysubstancesresearch.DrySubstancesResearch;
import com.example.demo.probe.research.drysubstancesresearch.DrySubstancesResearchForm;
import com.example.demo.probe.research.fatsresearch.FatsResearch;
import com.example.demo.probe.research.fatsresearch.FatsResearchForm;
import com.example.demo.probe.research.proteinsresearch.ProteinsResearch;
import com.example.demo.probe.research.proteinsresearch.ProteinsResearchForm;
import com.example.demo.probeingredient.ProbeIngredientGridLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle("Редактирование пробы")
@Route("probes/edit/:probeId")
public class ProbeEditView extends VerticalLayout implements BeforeEnterObserver {
    private final ProbeForm probeForm;
    private final DrySubstancesResearchForm drySubstancesResearchForm;
    private final FatsResearchForm fatsResearchForm;
    private final ProteinsResearchForm proteinsResearchForm;
    private final ProbeIngredientGridLayout probeIngredientGridLayout;
    private final ProbeService service;

    public ProbeEditView(ProbeService service, IngredientService ingredientService) {
        this.service = service;

        Card drySubstancesResearchCard = new Card();
        drySubstancesResearchForm = new DrySubstancesResearchForm();
        drySubstancesResearchCard.setTitle("Исследование на сухие остатки");
        drySubstancesResearchCard.add(drySubstancesResearchForm.component());

        Card fatsResearchCard = new Card();
        fatsResearchForm = new FatsResearchForm();
        fatsResearchCard.setTitle("Исследование на жиры");
        fatsResearchCard.add(fatsResearchForm.component());

        Card proteinsResearchCard = new Card();
        proteinsResearchForm = new ProteinsResearchForm();
        proteinsResearchCard.setTitle("Исследование на белки");
        proteinsResearchCard.add(proteinsResearchForm.component());

        HorizontalLayout actions = new HorizontalLayout(
            new CancelButton(event -> getUI().ifPresent(e -> e.navigate(ProbeView.class))),
            new SaveButton(this::save)
        );

        HorizontalLayout researches = new HorizontalLayout();
        researches.setSizeFull();
        researches.setWrap(true);
        setFlexGrow(1, drySubstancesResearchCard, fatsResearchCard, proteinsResearchCard);

        probeForm = new ProbeForm(true, e -> {
            switch (e.getValue()) {
                case FIRST, SECOND -> researches.add(drySubstancesResearchCard, fatsResearchCard, proteinsResearchCard);
                case THIRD -> researches.add(drySubstancesResearchCard, fatsResearchCard);
            }
        });

        probeIngredientGridLayout = new ProbeIngredientGridLayout(ingredientService.findAll());

        add(probeForm, researches, probeIngredientGridLayout.component(), actions);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RouteParameters parameters = event.getRouteParameters();
        Integer probeId = parameters.get("probeId").map(Integer::valueOf).orElseThrow();

        Optional<Probe> probeOptional = service.findById(probeId);
        if (probeOptional.isEmpty()) {
            event.rerouteTo(ProbeView.class);
            return;
        }

        Probe probe = probeOptional.get();
        probeForm.setEntity(probe);
        probeIngredientGridLayout.setProbe(probe);

        drySubstancesResearchForm.setResearch(probe.getDrySubstancesResearch());
        fatsResearchForm.setResearch(probe.getFatsResearch());
        proteinsResearchForm.setResearch(probe.getProteinsResearch());
    }

    private void save(ClickEvent<Button> event) {
        AtomicReference<DrySubstancesResearch> drySubstancesResearch = new AtomicReference<>();
        drySubstancesResearchForm.getResearch().ifPresent(drySubstancesResearch::set);

        AtomicReference<FatsResearch> fatsResearch = new AtomicReference<>();
        fatsResearchForm.getResearch().ifPresent(fatsResearch::set);

        AtomicReference<ProteinsResearch> proteinsResearch = new AtomicReference<>();
        proteinsResearchForm.getResearch().ifPresent(proteinsResearch::set);

        probeForm.getEntity().ifPresent(probe -> {
            probe.setDrySubstancesResearch(drySubstancesResearch.get());
            probe.setFatsResearch(fatsResearch.get());
            probe.setProteinsResearch(proteinsResearch.get());
            service.save(probe);
            getUI().ifPresent(e -> e.navigate(ProbeView.class));
        });
    }
}
