package com.marathonfront.view.user;

import com.marathonfront.domain.enumerated.Sex;
import com.marathonfront.domain.User;
import com.marathonfront.service.TeamService;
import com.marathonfront.service.UserService;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFormBinder {

    private final UserForm userForm;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserService userService = UserService.getInstance();
    private final TeamService teamService = TeamService.getInstance();

    public UserFormBinder(UserForm userForm) {

        this.userForm = userForm;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(userForm);

        binder.forField(userForm.getPassword())
                .withValidator(this::passwordValidator).bind("password");
        binder.forField(userForm.getEmail())
                .withValidator(this::emailValidator).bind("email");
        binder.forField(userForm.getFirstName())
                .withValidator(this::nameValidator).bind("firstName");
        binder.forField(userForm.getLastName())
                .withValidator(this::nameValidator).bind("lastName");
        binder.forField(userForm.getCity())
                .withValidator(this::nameValidator).bind("city");
        binder.forField(userForm.getBirthDate())
                .withValidator(this::dateValidator).bind("birthDate");
        binder.forField(userForm.getSex())
                .withValidator(this::sexValidator).bind("sex");
        binder.forField(userForm.getTeam())
                .withConverter(team -> team == null ? 0 : team.getId(),
                        teamId -> teamId == 0 ? null : teamService.getTeam(teamId))
                .bind("teamId");

        binder.setStatusLabel(userForm.getErrorMessageField());

        userForm.getSave().addClickListener(event -> {
            try {
                User userBean = userForm.getBinder().getBean();
                binder.writeBean(userBean);
                userService.saveUser(userBean);
                userForm.getUserView().refresh();
                userForm.setUser(null);
                showSuccess(userBean);
            } catch (ValidationException exception) {
                LOGGER.warn("Validation exception: " + exception.getMessage());
            }
        });
    }

    private ValidationResult passwordValidator(String password, ValueContext ctx) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            return ValidationResult.error("A password must be at least 8 characters. " +
                    "It has to have at least one letter and one digit.");
        } else {
            return ValidationResult.ok();
        }
    }

    private ValidationResult emailValidator(String email, ValueContext ctx) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return ValidationResult.error("This doesn't seem to be valid email address.");
        }
        if (userService.getAllUsers().stream().anyMatch(u -> u.getEmail().equals(email))
                && userForm.getBinder().getBean().getId() == null) {
            return ValidationResult.error("User identified with this email already exists in database.");
        }
        return ValidationResult.ok();
    }

    private ValidationResult nameValidator(String name, ValueContext ctx) {
        Pattern pattern = Pattern.compile("^[^- '](?=(?!\\p{Lu}?\\p{Lu}))(?=(?!\\p{Ll}+\\p{Lu}))(?=(?!.*\\p{Lu}\\p{Lu}))(?=(?!.*[- '][- '.]))(?=(?!.*[.][-'.]))(\\p{L}|[- '.]){2,}$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            return ValidationResult.error("Name should be 3-25 characters containing only letters and \'-\'");
        } else {
            return ValidationResult.ok();
        }
    }

    private ValidationResult dateValidator(LocalDate date, ValueContext ctx) {
        if (date == null) {
            return ValidationResult.error("Date must not be null");
        } else {
            return ValidationResult.ok();
        }
    }

    private ValidationResult sexValidator(Sex sex, ValueContext ctx) {
        if (sex == null) {
            return ValidationResult.error("Field must not be null");
        } else {
            return ValidationResult.ok();
        }
    }

    private void showSuccess(User userBean) {
        Notification notification =
                Notification.show("Data saved, user identified by email: " + userBean.getEmail());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
