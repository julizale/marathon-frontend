package com.marathonfront.view.performance;

import com.marathonfront.domain.Performance;
import com.marathonfront.domain.StartStatus;
import com.marathonfront.service.PerformanceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class PerformanceForm extends FormLayout {

    private PerformanceView performanceView;
    private PerformanceService performanceService = PerformanceService.getInstance();

    private TextField userId = new TextField("User id");
    private TextField raceId = new TextField("Race id");
    private ComboBox<Boolean> paid = new ComboBox<>("Paid");
    private TextField bibNumber = new TextField("Bib number");
    private ComboBox<StartStatus> status = new ComboBox<>("Status");
    private TextField timeGross = new TextField("Time gross");
    private TextField timeNet = new TextField("Time netto");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Performance> binder = new Binder<>(Performance.class);

    public PerformanceForm(PerformanceView performanceView) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        paid.setItems(Boolean.FALSE, Boolean.TRUE);
        status.setItems(StartStatus.values());
        add(userId, raceId, paid, bibNumber, status, timeGross, timeNet, buttons);
        binder.bindInstanceFields(this);
        this.performanceView = performanceView;
    }

    private void save(){
        Performance performance = binder.getBean();
        performanceService.savePerformance(performance);
        performanceView.refresh();
        setPerformance(null);
    }

    private void delete(){
        Performance performance = binder.getBean();
        performanceService.delete(performance);
        performanceView.refresh();
        setPerformance(null);
    }

    public void setPerformance(Performance performance) {
        binder.setBean(performance);

        if (performance == null) {
            setVisible(false);
        } else {
            setVisible(true);
            userId.focus();
        }
    }
}

