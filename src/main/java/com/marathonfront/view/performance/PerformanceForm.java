package com.marathonfront.view.performance;

import com.marathonfront.domain.*;
import com.marathonfront.domain.enumerated.StartStatus;
import com.marathonfront.service.PerformanceService;
import com.marathonfront.service.RaceService;
import com.marathonfront.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

@Getter
public class PerformanceForm extends FormLayout {

    private PerformanceView performanceView;
    private PerformanceService performanceService = PerformanceService.getInstance();
    private UserService userService = UserService.getInstance();
    private RaceService raceService = RaceService.getInstance();

    private ComboBox<User> user = new ComboBox<>("User");
    private ComboBox<Race> race = new ComboBox<>("Race");
    private ComboBox<Boolean> paid = new ComboBox<>("Paid");
    private IntegerField bibNumber = new IntegerField("Bib number");
    private ComboBox<StartStatus> status = new ComboBox<>("Status");
    private BigDecimalField timeGross = new BigDecimalField("Time gross");
    private BigDecimalField timeNet = new BigDecimalField("Time netto");
    private Span errorMessageField = new Span();

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Performance> binder = new Binder<>(Performance.class);

    public PerformanceForm(PerformanceView performanceView) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> delete());

        user.setItems(userService.getAllUsers());
        user.setItemLabelGenerator(User::getEmail);
        race.setItems(raceService.getAllRaces());
        race.setItemLabelGenerator(Race::getName);

        paid.setItems(Boolean.FALSE, Boolean.TRUE);
        status.setItems(StartStatus.values());

        add(user, race, paid, bibNumber, status, timeGross, timeNet, errorMessageField, buttons);

        binder.bindInstanceFields(this);
        this.performanceView = performanceView;
    }

    private void delete(){
        Performance performance = binder.getBean();
        performanceService.delete(performance);
        performanceView.refresh();
        setPerformance(null);
    }

    public void setPerformance(Performance performance) {
        binder.setBean(performance);

        if (performance == null ) {
            setVisible(false);
        } else {
            setVisible(true);
            user.setValue(performance.getUserId() != 0 ?
                    userService.getUser(performance.getUserId()) : null);
            race.setValue(performance.getRaceId() != 0 ?
                    raceService.getRace(performance.getRaceId()) : null);
            status.setValue(performance.getStatus() == null ?
                    StartStatus.DNS : performance.getStatus());
            user.focus();
        }
    }


}

