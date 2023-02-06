package com.marathonfront.view.team;

import com.marathonfront.domain.Team;
import com.marathonfront.service.TeamService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("team")
public class TeamView extends VerticalLayout {

    private final TeamService teamService = TeamService.getInstance();
    private Grid<Team> grid = new Grid<>(Team.class);
    private TeamForm teamForm = new TeamForm(this);
    private Button addNewTeam = new Button("Add new team");

    public TeamView() {
        teamForm.setTeam(null);
        addNewTeam.addClickListener(e -> {
            grid.asSingleSelect().clear();
            teamForm.setTeam(new Team());
        });
        grid.setColumns("id", "name");
        grid.setSizeFull();
        HorizontalLayout mainContent = new HorizontalLayout(grid, teamForm);
        mainContent.setSizeFull();
        add(addNewTeam, mainContent);
        setSizeFull();
        refresh();
        grid.asSingleSelect().addValueChangeListener(event -> teamForm.setTeam(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(teamService.getAllTeams());
    }
}
