package com.marathonfront.view;

import com.marathonfront.view.performance.PerformanceView;
import com.marathonfront.view.race.RaceView;
import com.marathonfront.view.team.TeamView;
import com.marathonfront.view.user.UserView;
import com.marathonfront.view.weather.WeatherView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route
public class MainView extends AppLayout {

    public MainView() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H2 logo = new H2();
        logo.addClassNames("text-l", "m-m");
        Image image = new Image("images/marathon.png", "Marathon");
        logo.add(image);

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink userLink = new RouterLink("User", UserView.class);
        userLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(userLink));

        RouterLink teamLink = new RouterLink("Team", TeamView.class);
        teamLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(teamLink));

        RouterLink raceLink = new RouterLink("Race", RaceView.class);
        raceLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(raceLink));

        RouterLink performanceLink = new RouterLink("Performance", PerformanceView.class);
        performanceLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(performanceLink));

        RouterLink weatherLink = new RouterLink("Weather", WeatherView.class);
        weatherLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(weatherLink));
    }
}
