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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.concurrent.atomic.AtomicReference;

@PageTitle("Добавление пробы")
@Route("probes/create")
public class ProbeCreateView extends VerticalLayout {
    private final ProbeForm probeForm;
    private final DrySubstancesResearchForm drySubstancesResearchForm;
    private final FatsResearchForm fatsResearchForm;
    private final ProteinsResearchForm proteinsResearchForm;
    private final ProbeService service;

    public ProbeCreateView(ProbeService service) {
        this.service = service;

        Card drySubstancesResearchCard = new Card();
        drySubstancesResearchForm = new DrySubstancesResearchForm();
        drySubstancesResearchForm.setFormDataObject(new DrySubstancesResearch());
        drySubstancesResearchCard.setTitle("Исследование на сухие остатки");
        drySubstancesResearchCard.add(drySubstancesResearchForm);

        Card fatsResearchCard = new Card();
        fatsResearchForm = new FatsResearchForm();
        fatsResearchForm.setFormDataObject(new FatsResearch());
        fatsResearchCard.setTitle("Исследование на жиры");
        fatsResearchCard.add(fatsResearchForm);

        Card proteinsResearchCard = new Card();
        proteinsResearchForm = new ProteinsResearchForm();
        proteinsResearchForm.setFormDataObject(new ProteinsResearch());
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

        probeForm = new ProbeForm(false, e -> {
            switch (e.getValue())  {
                case FIRST, SECOND -> {
                    researches.removeAll();
                    proteinsResearchForm.setFormDataObject(new ProteinsResearch());
                    researches.add(drySubstancesResearchCard, fatsResearchCard, proteinsResearchCard);
                }
                case THIRD -> {
                    researches.removeAll();
                    proteinsResearchForm.setFormDataObject(null);
                    researches.add(drySubstancesResearchCard, fatsResearchCard);
                }
            }
        });
        probeForm.setFormDataObject(new Probe());

        add(probeForm, researches, actions);
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
