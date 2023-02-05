package com.marathonfront.view.registration;

import com.marathonfront.domain.Sex;
import com.marathonfront.domain.Team;
import com.marathonfront.service.TeamService;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@Route("register")
public class RegistrationForm extends FormLayout {

    private final TeamService teamService = TeamService.getInstance();

    private H3 title = new H3("Registration form");

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField city = new TextField("City");
    private TextField postalCode = new TextField("Postal Code");

    private DatePicker birthDate = new DatePicker("Date of birth");

    private EmailField email = new EmailField("Email");
    private EmailField emailConfirm = new EmailField("Confirm email");

    private ComboBox<Sex> sex = new ComboBox<>("Sex");
    private ComboBox<Team> team = new ComboBox<>("Team");

    private PasswordField password = new PasswordField("Password");

    private PasswordField passwordConfirm = new PasswordField("Confirm password");

    private Span errorMessageField = new Span();

    private Button submitButton = new Button("Sign up");

    public RegistrationForm() {
        sex.setItems(Sex.values());
        team.setItems(teamService.getAllTeams());
        team.setItemLabelGenerator(Team::getName);
        team.setAllowCustomValue(true);

        setRequiredIndicatorVisible(firstName, lastName, email, emailConfirm,
                password, sex, passwordConfirm, city);

        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, firstName, lastName, email, emailConfirm, sex, team, postalCode, city,
                birthDate, password, passwordConfirm, errorMessageField, submitButton);

        setMaxWidth("500px");
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

        setColspan(title, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

}
