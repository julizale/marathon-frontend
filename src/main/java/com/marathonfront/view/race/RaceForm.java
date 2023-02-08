package com.marathonfront.view.race;

import com.marathonfront.domain.Race;
import com.marathonfront.service.RaceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class RaceForm extends FormLayout {

    private RaceView raceView;
    private RaceService raceService = RaceService.getInstance();

    private TextField name = new TextField("Name");
    private TextField distance = new TextField("Distance");
    private TextField price = new TextField("Price");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Race> binder = new Binder<Race>(Race.class);

    public RaceForm(RaceView raceView) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        add(name, distance, price, buttons);
        binder.bindInstanceFields(this);
        this.raceView = raceView;
    }

    private void save(){
        Race race = binder.getBean();
        raceService.createNewRace(race);
        raceView.refresh();
        setRace(null);
    }

    private void delete(){
        Race race = binder.getBean();
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