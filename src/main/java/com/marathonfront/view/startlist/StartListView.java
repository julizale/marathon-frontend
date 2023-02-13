package com.marathonfront.view.startlist;

import com.marathonfront.domain.*;
import com.marathonfront.service.PerformanceService;
import com.marathonfront.service.RaceService;
import com.marathonfront.service.TeamService;
import com.marathonfront.service.UserService;
import com.marathonfront.view.MainView;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "startlist", layout = MainView.class)
public class StartListView extends VerticalLayout {

    private final TeamService teamService = TeamService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final PerformanceService performanceService = PerformanceService.getInstance();
    private final RaceService raceService = RaceService.getInstance();
    private Grid<StartlistDisplayObject> grid = new Grid<>(StartlistDisplayObject.class);
    private ComboBox<Race> pickRace = new ComboBox<>("Pick a race");
    private H4 instruction = new H4("Select a race to view the list of competitors.");

    public StartListView() {
        pickRace.setItems(raceService.getAllRaces());
        pickRace.setItemLabelGenerator(Race::getName);
        pickRace.addValueChangeListener(event -> refresh());
        grid.setColumns("bibNumber", "firstName", "lastName", "sex", "city", "birthYear", "teamName");
        grid.setSizeFull();
        HorizontalLayout upperPanel = new HorizontalLayout(pickRace, instruction);
        add(upperPanel, grid);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        if (pickRace.getValue() == null) {
            return;
        }

        List<StartlistDisplayObject> startList = new ArrayList<>();
        List<Performance> performances = performanceService.getAllPerformances().stream()
                .filter(p -> p.getRaceId() == pickRace.getValue().getId())
                .toList();

        for (Performance performance : performances) {

            StartlistDisplayObject startObject = new StartlistDisplayObject();
            User user = userService.getUser(performance.getUserId());

            if (user.getTeamId() != null && user.getTeamId() != 0) {
                startObject.setTeamName(teamService.getTeam(user.getTeamId()).getName());
            }
            if (performance.getBibNumber() != null && performance.getBibNumber() != 0) {
                startObject.setBibNumber(performance.getBibNumber());
            }
            startObject.setFirstName(user.getFirstName());
            startObject.setLastName(user.getLastName());
            startObject.setCity(user.getCity());
            startObject.setSex(user.getSex());
            startObject.setBirthYear(user.getBirthDate().getYear());

            startList.add(startObject);
        }
        grid.setItems(startList);
    }
}
