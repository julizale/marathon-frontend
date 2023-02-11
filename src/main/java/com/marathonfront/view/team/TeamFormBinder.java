package com.marathonfront.view.team;

import com.marathonfront.domain.Team;
import com.marathonfront.service.TeamService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeamFormBinder {

    private final TeamForm teamForm;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamFormBinder.class);
    private final TeamService teamService = TeamService.getInstance();

    public TeamFormBinder(TeamForm teamForm) {

        this.teamForm = teamForm;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<Team> binder = new BeanValidationBinder<>(Team.class);
        binder.bindInstanceFields(teamForm);

        binder.forField(teamForm.getName())
                .withValidator(this::nameValidator).bind("name");

        binder.setStatusLabel(teamForm.getErrorMessageField());

        teamForm.getSave().addClickListener(event -> {
            try {
                Team teamBean = teamForm.getBinder().getBean();
                binder.writeBean(teamBean);
                teamService.saveTeam(teamBean);
                teamForm.getTeamView().refresh();
                teamForm.setTeam(null);
                showSuccess(teamBean);
            } catch (ValidationException exception) {
                LOGGER.warn("Validation exception: " + exception.getMessage());
            }
        });
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

    private void showSuccess(Team teamBean) {
        Notification notification =
                Notification.show("Data saved.");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
