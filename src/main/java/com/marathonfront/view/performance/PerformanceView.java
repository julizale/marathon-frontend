package com.marathonfront.view.performance;

import com.marathonfront.domain.Performance;
import com.marathonfront.domain.Race;
import com.marathonfront.service.PerformanceService;
import com.marathonfront.service.RaceService;
import com.marathonfront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "performance", layout = MainView.class)
public class PerformanceView extends VerticalLayout {

    private final PerformanceService performanceService = PerformanceService.getInstance();
    private final RaceService raceService = RaceService.getInstance();
    private Grid<Performance> grid = new Grid<>(Performance.class);
    private PerformanceForm performanceForm = new PerformanceForm(this);
    private Button addUpdatePerformance = new Button("Add new performance");
    private H4 instruction = new H4("To add a new performance, click the button on the left." +
            " To edit or delete a performance, click it on the list.");


    public PerformanceView() {
        performanceForm.setPerformance(null);
        addUpdatePerformance.addClickListener(e -> {
            grid.asSingleSelect().clear();
            performanceForm.setPerformance(new Performance());
        });
        grid.setColumns("id", "userId", "raceId", "paid", "bibNumber", "status", "timeGross", "timeNet");
        grid.setSizeFull();
        HorizontalLayout upperPanel = new HorizontalLayout(addUpdatePerformance, instruction);
        HorizontalLayout mainContent = new HorizontalLayout(grid, performanceForm);
        mainContent.setSizeFull();
        add(upperPanel, mainContent);
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
