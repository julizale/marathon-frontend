package com.marathonfront.view.registration;

import com.marathonfront.domain.User;
import com.marathonfront.service.UserService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationFormBinder {

    private final RegistrationForm registrationForm;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserService userService = UserService.getInstance();

    private boolean enablePasswordValidation;
    private boolean enableEmailValidation;

    public RegistrationFormBinder(RegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
    }

    public void addBindingAndValidation() {
        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.bindInstanceFields(registrationForm);

        binder.forField(registrationForm.getPassword())
                .withValidator(this::passwordValidator).bind("password");
        binder.forField(registrationForm.getEmail())
                .withValidator(this::emailValidator).bind("email");

        registrationForm.getPasswordConfirm().addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        registrationForm.getEmailConfirm().addValueChangeListener(e -> {
            enableEmailValidation = true;
            binder.validate();
        });

        binder.setStatusLabel(registrationForm.getErrorMessageField());

        registrationForm.getSubmitButton().addClickListener(event -> {
            try {
                User userBean = new User();
                binder.writeBean(userBean);
                userService.saveUser(userBean);
                showSuccess(userBean);
            } catch (ValidationException exception) {
                LOGGER.warn("Validation exception: " + exception.getMessage());
            }
        });
    }

    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }
        String pass2 = registrationForm.getPasswordConfirm().getValue();
        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }
        return ValidationResult.error("Passwords do not match");
    }

    private ValidationResult emailValidator(String email1, ValueContext ctx) {
        if (!enableEmailValidation) {
            enableEmailValidation = true;
            return ValidationResult.ok();
        }
        String email2 = registrationForm.getEmail().getValue();
        if (email1 != null && email1.equals(email2)) {
            return ValidationResult.ok();
        }
        return ValidationResult.error("Emails do not match");
    }

    private void showSuccess(User userBean) {
        Notification notification =
                Notification.show("Data saved, welcome " + userBean.getFirstName());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        // Here you'd typically redirect the user to another view
    }

}