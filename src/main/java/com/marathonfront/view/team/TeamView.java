package com.marathonfront.view.team;

import com.marathonfront.domain.Team;
import com.marathonfront.service.TeamService;
import com.marathonfront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "team", layout = MainView.class)
public class TeamView extends VerticalLayout {

    private final TeamService teamService = TeamService.getInstance();
    private Grid<Team> grid = new Grid<>(Team.class);
    private TeamForm teamForm = new TeamForm(this);
    private Button addNewTeam = new Button("Add new team");
    private H4 instruction = new H4("To add a new team, click the button on the left." +
            " To edit or delete a team, click it on the list.");

    public TeamView() {
        teamForm.setTeam(null);
        addNewTeam.addClickListener(e -> {
            grid.asSingleSelect().clear();
            teamForm.setTeam(new Team());
        });
        grid.setColumns("id", "name");
        grid.setSizeFull();
        HorizontalLayout upperPanel = new HorizontalLayout(addNewTeam, instruction);
        HorizontalLayout mainContent = new HorizontalLayout(grid, teamForm);
        mainContent.setSizeFull();
        add(upperPanel, mainContent);
        setSizeFull();
        refresh();
        grid.asSingleSelect().addValueChangeListener(event -> teamForm.setTeam(grid.asSingleSelect().getValue()));

        TeamFormBinder teamFormBinder = new TeamFormBinder(teamForm);
        teamFormBinder.addBindingAndValidation();
    }

    public void refresh() {
        grid.setItems(teamService.getAllTeams());
    }

}
