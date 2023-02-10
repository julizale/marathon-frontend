package com.marathonfront.view.performance;

import com.marathonfront.domain.Performance;
import com.marathonfront.domain.User;
import com.marathonfront.service.PerformanceService;
import com.marathonfront.view.MainView;
import com.marathonfront.view.user.UserFormBinder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "performance", layout = MainView.class)
public class PerformanceView extends VerticalLayout {

    private final PerformanceService performanceService = PerformanceService.getInstance();
    private Grid<Performance> grid = new Grid<>(Performance.class);
    private PerformanceForm performanceForm = new PerformanceForm(this);
    private Button addUpdatePerformance = new Button("Add new performance");

    public PerformanceView() {
        performanceForm.setPerformance(null);
//        addNewUser.addClickListener(e -> {
//            grid.asSingleSelect().clear();
//            userForm.setUser(new User());
//        });
        addUpdatePerformance.addClickListener(e -> {
            grid.asSingleSelect().clear();
            performanceForm.setPerformance(new Performance());
        });
        grid.setColumns("userId", "raceId", "paid", "bibNumber", "status", "timeGross", "timeNet");
        grid.setSizeFull();
        HorizontalLayout mainContent = new HorizontalLayout(grid, performanceForm);
        mainContent.setSizeFull();
        add(addUpdatePerformance, mainContent);
        setSizeFull();
        refresh();
        grid.asSingleSelect().addValueChangeListener(event -> performanceForm.setPerformance(grid.asSingleSelect().getValue()));

        PerformanceFormBinder performanceFormBinder = new PerformanceFormBinder(performanceForm);
        performanceFormBinder.addBindingAndValidation();
    }

    public void refresh() {
        grid.setItems(performanceService.getAllPerformances());
    }
}
