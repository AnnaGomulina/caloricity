package ru.caloricity.probe;

import ru.caloricity.common.CancelButton;
import ru.caloricity.common.SaveButton;
import ru.caloricity.common.Updater;
import ru.caloricity.ingredient.IngredientService;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearch;
import ru.caloricity.probe.research.drysubstancesresearch.DrySubstancesResearchForm;
import ru.caloricity.probe.research.fatsresearch.FatsResearch;
import ru.caloricity.probe.research.fatsresearch.FatsResearchForm;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearch;
import ru.caloricity.probe.research.proteinsresearch.ProteinsResearchForm;
import ru.caloricity.probeingredient.ProbeIngredientGridLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
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

    public ProbeCreateView(ProbeService service, IngredientService ingredientService) {
        this.service = service;

        Card drySubstancesResearchCard = new Card();
        drySubstancesResearchForm = new DrySubstancesResearchForm();
        drySubstancesResearchForm.setResearch(new DrySubstancesResearch());
        drySubstancesResearchCard.setTitle("Исследование на сухие остатки");
        drySubstancesResearchCard.add(drySubstancesResearchForm.component());

        Card fatsResearchCard = new Card();
        fatsResearchForm = new FatsResearchForm();
        fatsResearchForm.setResearch(new FatsResearch());
        fatsResearchCard.setTitle("Исследование на жиры");
        fatsResearchCard.add(fatsResearchForm.component());

        Card proteinsResearchCard = new Card();
        proteinsResearchForm = new ProteinsResearchForm();
        proteinsResearchForm.setResearch(new ProteinsResearch());
        proteinsResearchCard.setTitle("Исследование на белки");
        proteinsResearchCard.add(proteinsResearchForm.component());

        HorizontalLayout researches = new HorizontalLayout();
        researches.setSizeFull();
        researches.setWrap(true);
        setFlexGrow(1, drySubstancesResearchCard, fatsResearchCard, proteinsResearchCard);

        Updater updater = new Updater();
        probeForm = new ProbeForm(false, updater, e -> {
            switch (e.getValue()) {
                case FIRST, SECOND -> {
                    researches.removeAll();
                    proteinsResearchForm.setResearch(new ProteinsResearch());
                    researches.add(drySubstancesResearchCard, fatsResearchCard, proteinsResearchCard);
                }
                case THIRD -> {
                    researches.removeAll();
                    proteinsResearchForm.setResearch(null);
                    researches.add(drySubstancesResearchCard, fatsResearchCard);
                }
            }
        });
        Probe probe = new Probe();
        updater.setProbe(probe);
        probeForm.setEntity(probe);

        ProbeIngredientGridLayout probeIngredientGridLayout = new ProbeIngredientGridLayout(ingredientService.findAll(), updater);
        probeIngredientGridLayout.setProbe(probe);

        HorizontalLayout actions = new HorizontalLayout(
            new CancelButton(event -> getUI().ifPresent(e -> e.navigate(ProbeView.class))),
            new SaveButton(this::save)
        );

        add(probeForm, researches, probeIngredientGridLayout.component(), actions);
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
