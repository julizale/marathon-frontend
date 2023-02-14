package com.marathonfront.view.user;

import com.marathonfront.domain.Performance;
import com.marathonfront.domain.Race;
import com.marathonfront.domain.enumerated.Sex;
import com.marathonfront.domain.Team;
import com.marathonfront.domain.User;
import com.marathonfront.domain.enumerated.StartStatus;
import com.marathonfront.service.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class UserForm extends FormLayout {

    private UserView userView;
    private UserService userService = UserService.getInstance();
    private TeamService teamService = TeamService.getInstance();
    private RaceService raceService = RaceService.getInstance();
    private PerformanceService performanceService = PerformanceService.getInstance();

    private TextField email = new TextField("Email");
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField city = new TextField("City");
    private TextField postalCode = new TextField("Postal Code");
    private DatePicker birthDate = new DatePicker("Date of birth");
    private ComboBox<Sex> sex = new ComboBox<>("Sex");
    private ComboBox<Team> team = new ComboBox<>("Team");
    private PasswordField password = new PasswordField("Password");

    private Button save = new Button("Save User data");
    private Button delete = new Button("Delete user");
    private Span errorMessageField = new Span();
    private Binder<User> binder = new Binder<>(User.class);

    private Button signToRace = new Button("Sign to race");
    private ComboBox<Race> race = new ComboBox<>();

    public UserForm(UserView userView) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> delete());
        postalCode.addValueChangeListener(event -> getPostalUpdateCity());
        sex.setItems(Sex.values());
        team.setItems(teamService.getAllTeams());
        team.setItemLabelGenerator(Team::getName);
        race.setItems(raceService.getAllRaces());
        race.setItemLabelGenerator(Race::getName);
        signToRace.addClickListener(event -> signUserToRace());
        HorizontalLayout signToRacePanel = new HorizontalLayout(race, signToRace);
        add(email, firstName, lastName,  birthDate, postalCode, city, sex, team, password,
                errorMessageField, buttons, signToRacePanel);
        setColspan(buttons, 2);
        setColspan(signToRacePanel, 2);
        binder.bindInstanceFields(this);
        this.userView = userView;
    }

    private void delete(){
        User user = binder.getBean();

        if (PerformanceService.getInstance().getAllPerformances().stream()
                .anyMatch(p -> p.getUserId() == user.getId())) {
            Notification notification = Notification.show("User has signed up for race!" +
                    "Delete performance for this user first.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

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
            team.setValue(user.getTeamId() != 0 ?
                    teamService.getTeam(user.getTeamId()) : null);
            postalCode.setValue("");
            setRaceCombo(user);
            email.focus();
        }
    }

    private void setRaceCombo(User user) {
        if (user.getPerformanceId() == null || user.getPerformanceId() == 0) {
            race.setValue(null);
        } else {
            race.setValue(raceService.getRace(performanceService.getPerformance(
                    user.getPerformanceId()).getRaceId()));
        }
    }

    private void getPostalUpdateCity() {
        String code = postalCode.getValue();
        Pattern pattern = Pattern.compile("\\d{2}-\\d{3}");
        Matcher matcher = pattern.matcher(code);
        if (matcher.matches()) {
            String retrievedCity = PostalCodeService.getInstance().getCity(code);
            city.setValue(retrievedCity);
        }
    }

    private void signUserToRace() {
        if (race.getValue() == null) {
            return;
        }
        Performance performance;
        Long performanceId = binder.getBean().getPerformanceId();
        if (performanceId != null && performanceId != 0) {
            performance = performanceService.getPerformance(performanceId);
            performance.setRaceId(race.getValue().getId());
        } else {
            performance = new Performance();
            performance.setUserId(binder.getBean().getId());
            performance.setRaceId(race.getValue().getId());
            performance.setPaid(false);
            performance.setStatus(StartStatus.DNS);
        }
        performanceService.savePerformance(performance);
        Notification notification =
                Notification.show("User signed to " + race.getValue().getName());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        setUser(null);
    }
}
