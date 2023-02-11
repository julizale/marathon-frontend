package com.marathonfront.view.user;

import com.marathonfront.domain.User;
import com.marathonfront.service.UserService;
import com.marathonfront.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "user", layout = MainView.class)
public class UserView extends VerticalLayout {

    private final UserService userService = UserService.getInstance();
    private Grid<User> grid = new Grid<>(User.class);
    private UserForm userForm = new UserForm(this);
    private Button addNewUser = new Button("Add new user");
    private H4 instruction = new H4("To add a new user, click the button on the left." +
            " To edit or delete a user, click on the list.");

    public UserView() {
        userForm.setUser(null);
        addNewUser.addClickListener(e -> {
            grid.asSingleSelect().clear();
            userForm.setUser(new User());
        });
        grid.setColumns("email", "firstName", "lastName", "birthDate", "sex", "city", "teamId");
        grid.setSizeFull();
        HorizontalLayout upperPanel = new HorizontalLayout(addNewUser, instruction);
        HorizontalLayout mainContent = new HorizontalLayout(grid, userForm);
        mainContent.setSizeFull();
        add(upperPanel, mainContent);
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
