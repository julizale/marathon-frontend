package com.marathonfront.view.performance;

import com.marathonfront.domain.*;
import com.marathonfront.service.PerformanceService;
import com.marathonfront.service.RaceService;
import com.marathonfront.service.UserService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceFormBinder {

    private final PerformanceForm performanceForm;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final PerformanceService performanceService = PerformanceService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final RaceService raceService = RaceService.getInstance();

    public PerformanceFormBinder(PerformanceForm performanceForm) {

        this.performanceForm = performanceForm;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<Performance> binder = new BeanValidationBinder<>(Performance.class);
        binder.bindInstanceFields(performanceForm);

        binder.forField(performanceForm.getUser())
                .withValidator(this::userValidator)
                .withConverter(User::getId, userService::getUser)
                .bind("userId");

        binder.forField(performanceForm.getRace())
                .withValidator(this::raceValidator)
                .withConverter(Race::getId, raceService::getRace)
                .bind("raceId");

        binder.forField(performanceForm.getPaid())
                .withValidator(this::paidValidator).bind("paid");
        binder.forField(performanceForm.getStatus())
                .withValidator(this::statusValidator).bind("status");

        binder.setStatusLabel(performanceForm.getErrorMessageField());

        performanceForm.getSave().addClickListener(event -> {
            try {
                Performance performanceBean = performanceForm.getBinder().getBean();
                binder.writeBean(performanceBean);
                performanceService.savePerformance(performanceBean);
                performanceForm.getPerformanceView().refresh();
                showSuccess(performanceBean);
            } catch (ValidationException exception) {
                LOGGER.warn("Validation exception: " + exception.getMessage());
            }
        });
    }

    private ValidationResult userValidator(User user, ValueContext ctx) {
        if (user == null) {
            return ValidationResult.error("Field must not be null");
        }
        if (performanceService.getAllPerformances().stream().anyMatch(p -> p.getUserId() == user.getId())
                && performanceForm.getBinder().getBean().getId() == 0) {
            return ValidationResult.error("User is already assigned to another performance.");
        }
        return ValidationResult.ok();
    }

    private ValidationResult raceValidator(Race race, ValueContext ctx) {
        if (race == null) {
            return ValidationResult.error("Field must not be null");
        } else {
            return ValidationResult.ok();
        }
    }

    private ValidationResult paidValidator(Boolean paid, ValueContext ctx) {
        if (paid == null) {
            return ValidationResult.error("Field must not be null");
        } else {
            return ValidationResult.ok();
        }
    }

    private ValidationResult statusValidator(StartStatus status, ValueContext ctx) {
        if (status == null) {
            return ValidationResult.error("Field must not be null");
        } else {
            return ValidationResult.ok();
        }
    }

    private void showSuccess(Performance performanceBean) {
        Notification notification =
                Notification.show("Data saved");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
