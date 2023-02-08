package com.marathonfront.view.race;

import com.marathonfront.domain.Race;
import com.marathonfront.service.RaceService;
import com.marathonfront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "race", layout = MainView.class)
public class RaceView extends VerticalLayout {

    private final RaceService raceService = RaceService.getInstance();
    private Grid<Race> grid = new Grid<>(Race.class);
    private RaceForm raceForm = new RaceForm(this);
    private Button addNewRace = new Button("Add new race");

    public RaceView() {
        raceForm.setRace(null);
        addNewRace.addClickListener(e -> {
            grid.asSingleSelect().clear();
            raceForm.setRace(new Race());
        });
        grid.setColumns("name", "distance", "price");
        grid.setSizeFull();
        HorizontalLayout mainContent = new HorizontalLayout(grid, raceForm);
        mainContent.setSizeFull();
        add(addNewRace, mainContent);
        setSizeFull();
        refresh();
        grid.asSingleSelect().addValueChangeListener(event -> raceForm.setRace(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(raceService.getAllRaces());
    }
}
