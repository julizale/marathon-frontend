package com.marathonfront.view.team;

import com.marathonfront.domain.Race;
import com.marathonfront.domain.Team;
import com.marathonfront.service.RaceService;
import com.marathonfront.service.TeamService;
import com.marathonfront.view.race.RaceView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class TeamForm extends FormLayout {

    private TeamView teamView;
    private TeamService teamService = TeamService.getInstance();

    private TextField name = new TextField("Name");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Team> binder = new Binder<>(Team.class);

    public TeamForm(TeamView teamView) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        add(name, buttons);
        binder.bindInstanceFields(this);
        this.teamView = teamView;
    }

    private void save(){
        Team team = binder.getBean();
        teamService.createNewTeam(team);
        teamView.refresh();
        setTeam(null);
    }

    private void delete(){
        Team team = binder.getBean();
        teamService.delete(team);
        teamView.refresh();
        setTeam(null);
    }

    public void setTeam(Team team) {
        binder.setBean(team);

        if (team == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

}
