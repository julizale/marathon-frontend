package com.marathonfront.view.race;

import com.marathonfront.domain.Race;
import com.marathonfront.service.PerformanceService;
import com.marathonfront.service.RaceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.Getter;

@Getter
public class RaceForm extends FormLayout {

    private RaceView raceView;
    private RaceService raceService = RaceService.getInstance();

    private TextField name = new TextField("Name");
    private IntegerField distance = new IntegerField("Distance");
    private BigDecimalField price = new BigDecimalField("Price");
    private Span errorMessageField = new Span();

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Race> binder = new Binder<Race>(Race.class);

    public RaceForm(RaceView raceView) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> delete());
        add(name, distance, price, errorMessageField, buttons);
        binder.bindInstanceFields(this);
        this.raceView = raceView;
    }

    private void delete(){
        Race race = binder.getBean();
        if (PerformanceService.getInstance().getAllPerformances().stream()
                .anyMatch(p -> p.getRaceId() == race.getId())) {
            Notification notification = Notification.show("There are users signed up for this race!" +
                    " Delete all performances for this race first.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        raceService.delete(race);
        raceView.refresh();
        setRace(null);
    }

    public void setRace(Race race) {
        binder.setBean(race);

        if (race == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }
}
