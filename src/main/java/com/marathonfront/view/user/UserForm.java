package com.marathonfront.view.user;

import com.marathonfront.domain.Sex;
import com.marathonfront.domain.Team;
import com.marathonfront.domain.User;
import com.marathonfront.service.TeamService;
import com.marathonfront.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

@Getter
public class UserForm extends FormLayout {

    private UserView userView;
    private UserService userService = UserService.getInstance();
    private TeamService teamService = TeamService.getInstance();

    private TextField email = new TextField("Email");
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField city = new TextField("City");
    private DatePicker birthDate = new DatePicker("Date of birth");
    private ComboBox<Sex> sex = new ComboBox<>("Sex");
    private ComboBox<Team> team = new ComboBox<>("Team");
    private PasswordField password = new PasswordField("Password");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Span errorMessageField = new Span();
    private Binder<User> binder = new Binder<>(User.class);

    public UserForm(UserView userView) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> delete());
        sex.setItems(Sex.values());
        team.setItems(teamService.getAllTeams());
        team.setItemLabelGenerator(Team::getName);
        add(email, firstName, lastName, city, birthDate, sex, team, password, errorMessageField, buttons);
        binder.bindInstanceFields(this);
        this.userView = userView;
    }

    private void delete(){
        User user = binder.getBean();
        userService.delete(user);
        userView.refresh();
        setUser(null);
    }

    public void setUser(User user) {
        binder.setBean(user);

        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            if (user.getTeamId() != 0) {
                team.setValue(teamService.getTeam(user.getTeamId()));
            }
            email.focus();
        }
    }
}
