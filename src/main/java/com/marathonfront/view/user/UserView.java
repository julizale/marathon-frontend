package com.marathonfront.view.user;

import com.marathonfront.domain.User;
import com.marathonfront.service.UserService;
import com.marathonfront.view.MainView;
import com.marathonfront.view.registration.RegistrationFormBinder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "user", layout = MainView.class)
public class UserView extends VerticalLayout {

    private final UserService userService = UserService.getInstance();
    private Grid<User> grid = new Grid<>(User.class);
    private UserForm userForm = new UserForm(this);
    private Button addNewUser = new Button("Add new user");

    public UserView() {
        userForm.setUser(null);
        addNewUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            userForm.setUser(new User());
        });
        grid.setColumns("email", "firstName", "lastName", "birthDate", "sex", "city", "teamId");
        grid.setSizeFull();
        HorizontalLayout mainContent = new HorizontalLayout(grid, userForm);
        mainContent.setSizeFull();
        add(addNewUser, mainContent);
        setSizeFull();
        refresh();
        grid.asSingleSelect().addValueChangeListener(event -> userForm.setUser(grid.asSingleSelect().getValue()));

        UserFormBinder userFormBinder = new UserFormBinder(userForm);
        userFormBinder.addBindingAndValidation();
    }

    public void refresh() {
        grid.setItems(userService.getAllUsers());
    }
}
