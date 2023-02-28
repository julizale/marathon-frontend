package com.marathonfront.view.weather;

import com.marathonfront.domain.weather.WeatherDay;
import com.marathonfront.service.WeatherService;
import com.marathonfront.view.MainView;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route(value = "weather", layout = MainView.class)
public class WeatherView extends VerticalLayout {

    private final WeatherService weatherService = WeatherService.getInstance();
    private Grid<WeatherDay> grid = new Grid<>(WeatherDay.class);
    private DatePicker datePicker = new DatePicker("Marathon day");
    private H4 weatherInstruction = new H4("Select a race date (within the coming year) " +
            "to see the weather on that date from the last 10 years\n" +
            "in Częstochowa, Poland, where the race will take place");

    public WeatherView() {

        grid.setColumns("date", "temperature", "feelslike", "preciptation", "windspeed");
        grid.setSizeFull();
        datePicker.setMin(LocalDate.now());
        datePicker.setMax(LocalDate.now().plusYears(1L));
        datePicker.setHelperText("Must be within 1 year from today");
        datePicker.addValueChangeListener(event -> refresh(datePicker.getValue()));
        HorizontalLayout upperPanel = new HorizontalLayout(datePicker, weatherInstruction);
        add(upperPanel, grid);
        setSizeFull();
        refresh(datePicker.getValue());
    }

    public void refresh(LocalDate date) {
        if (date == null) return;
        List<WeatherDay> weatherDays = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            WeatherDay weatherDay = weatherService.getWeatherDay(date.minusYears(i));
            if (weatherDay != null) {
                weatherDays.add(weatherDay);
            }
        }
        grid.setItems(weatherDays);
    }

}
