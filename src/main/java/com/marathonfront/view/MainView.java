package com.marathonfront.view;

import com.marathonfront.domain.User;
import com.marathonfront.service.UserService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route
@PageTitle("Marathon")
public class MainView extends VerticalLayout {

    private final UserService userService = UserService.getInstance();
    private Grid<User> grid = new Grid<>(User.class);

    public MainView() {
        grid.setColumns("id", "email", "firstName", "lastName", "birthDate", "sex", "city");
        add(grid);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        grid.setItems(userService.getAllUsers());
    }
}
