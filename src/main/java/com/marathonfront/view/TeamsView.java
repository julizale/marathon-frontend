package com.marathonfront.view;

import com.marathonfront.domain.Team;
import com.marathonfront.service.TeamService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("team")
public class TeamsView extends VerticalLayout {

    private final TeamService teamService = TeamService.getInstance();
    private Grid<Team> grid = new Grid<>(Team.class);

    public TeamsView() {
        grid.setColumns("id", "name");
        add(grid);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        grid.setItems(teamService.getAllTeams());
    }
}
