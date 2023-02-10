package com.marathonfront.view.race;

import com.marathonfront.domain.Race;
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

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RaceFormBinder {

    private final RaceForm raceForm;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final RaceService raceService = RaceService.getInstance();

    public RaceFormBinder(RaceForm raceForm) {

        this.raceForm = raceForm;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<Race> binder = new BeanValidationBinder<>(Race.class);
        binder.bindInstanceFields(raceForm);

        binder.forField(raceForm.getName())
                .withValidator(this::nameValidator).bind("name");
        binder.forField(raceForm.getDistance())
                .withValidator(this::distanceValidator).bind("distance");
        binder.forField(raceForm.getPrice())
                .withValidator(this::priceValidator).bind("price");

        binder.setStatusLabel(raceForm.getErrorMessageField());

        raceForm.getSave().addClickListener(event -> {
            try {
                Race raceBean = raceForm.getBinder().getBean();
                binder.writeBean(raceBean);
                raceService.saveRace(raceBean);
                raceForm.getRaceView().refresh();
                showSuccess(raceBean);
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

    private ValidationResult priceValidator(BigDecimal price, ValueContext ctx) {
        if (price == null) {
            return ValidationResult.error("Field must not be null");
        } else {
            return ValidationResult.ok();
        }
    }

    private ValidationResult distanceValidator(Integer distance, ValueContext ctx) {
        if (distance == null) {
            return ValidationResult.error("Field must not be null");
        } else {
            return ValidationResult.ok();
        }
    }

    private void showSuccess(Race raceBean) {
        Notification notification =
                Notification.show("Data saved.");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
