package com.example.demo.probe;

import com.example.demo.probe.research.drysubstancesresearch.DrySubstancesResearch;
import com.example.demo.probe.research.drysubstancesresearch.DrySubstancesResearchForm;
import com.example.demo.probe.research.fatsresearch.FatsResearch;
import com.example.demo.probe.research.fatsresearch.FatsResearchForm;
import com.example.demo.probe.research.proteinsresearch.ProteinsResearch;
import com.example.demo.probe.research.proteinsresearch.ProteinsResearchForm;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.card.Card;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@PageTitle("Редактирование пробы")
@Route("probes/edit/:probeId")
public class ProbeEditView extends VerticalLayout implements BeforeEnterObserver {
    private final ProbeForm probeForm;
    private final DrySubstancesResearchForm drySubstancesResearchForm;
    private final FatsResearchForm fatsResearchForm;
    private final ProteinsResearchForm proteinsResearchForm;
    private final ProbeService service;

    public ProbeEditView(ProbeService service) {
        this.service = service;

        Card drySubstancesResearchCard = new Card();
        drySubstancesResearchForm = new DrySubstancesResearchForm();
        drySubstancesResearchCard.setTitle("Исследование на сухие остатки");
        drySubstancesResearchCard.add(drySubstancesResearchForm);

        Card fatsResearchCard = new Card();
        fatsResearchForm = new FatsResearchForm();
        fatsResearchCard.setTitle("Исследование на жиры");
        fatsResearchCard.add(fatsResearchForm);

        Card proteinsResearchCard = new Card();
        proteinsResearchForm = new ProteinsResearchForm();
        proteinsResearchCard.setTitle("Исследование на белки");
        proteinsResearchCard.add(proteinsResearchForm);

        Button saveButton = new Button("Сохранить", this::save);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Отмена", event -> getUI().ifPresent(e -> e.navigate(ProbeView.class)));
        HorizontalLayout actions = new HorizontalLayout(cancelButton, saveButton);

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

        add(probeForm, researches, actions);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RouteParameters parameters = event.getRouteParameters();
        UUID probeId = parameters.get("probeId").map(UUID::fromString).orElseThrow();

        Optional<Probe> probeOptional = service.findById(probeId);
        if (probeOptional.isEmpty()) {
            event.rerouteTo(ProbeView.class);
            return;
        }

        Probe probe = probeOptional.get();
        probeForm.setFormDataObject(probe);

        if (probe.getDrySubstancesResearch() != null) {
            drySubstancesResearchForm.setFormDataObject(probe.getDrySubstancesResearch());
        } else {
            drySubstancesResearchForm.setFormDataObject(new DrySubstancesResearch());
        }

        if (probe.getFatsResearch() != null) {
            fatsResearchForm.setFormDataObject(probe.getFatsResearch());
        } else {
            fatsResearchForm.setFormDataObject(new FatsResearch());
        }

        if (probe.getProteinsResearch() != null) {
            proteinsResearchForm.setFormDataObject(probe.getProteinsResearch());
        } else {
            proteinsResearchForm.setFormDataObject(new ProteinsResearch());
        }
    }

    private void save(ClickEvent<Button> event) {
        AtomicReference<DrySubstancesResearch> drySubstancesResearch = new AtomicReference<>();
        drySubstancesResearchForm.getFormDataObject().ifPresent(drySubstancesResearch::set);

        AtomicReference<FatsResearch> fatsResearch = new AtomicReference<>();
        fatsResearchForm.getFormDataObject().ifPresent(fatsResearch::set);

        AtomicReference<ProteinsResearch> proteinsResearch = new AtomicReference<>();
        proteinsResearchForm.getFormDataObject().ifPresent(proteinsResearch::set);

        probeForm.getFormDataObject().ifPresent(probe -> {
            probe.setDrySubstancesResearch(drySubstancesResearch.get());
            probe.setFatsResearch(fatsResearch.get());
            probe.setProteinsResearch(proteinsResearch.get());
            service.save(probe);
            getUI().ifPresent(e -> e.navigate(ProbeView.class));
        });
    }
}
