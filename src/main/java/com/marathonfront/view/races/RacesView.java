package com.marathonfront.view.races;

import com.marathonfront.domain.Race;
import com.marathonfront.service.RaceService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("race")
public class RacesView extends VerticalLayout {

    private final RaceService raceService = RaceService.getInstance();
    private Grid<Race> grid = new Grid<>(Race.class);
    private RaceForm raceForm = new RaceForm(this);
    private Button addNewRace = new Button("Add new race");

    public RacesView() {
        raceForm.setRace(null);
        addNewRace.addClickListener(e -> {
            grid.asSingleSelect().clear();
            raceForm.setRace(new Race());
        });
        grid.setColumns("name", "distance", "price");
        grid.setSizeFull();
        HorizontalLayout mainContent = new HorizontalLayout(grid, raceForm);
        mainContent.setSizeFull();
        add(addNewRace, mainContent);
        setSizeFull();
        refresh();
        grid.asSingleSelect().addValueChangeListener(event -> raceForm.setRace(grid.asSingleSelect().getValue()));
    }

    public void refresh() {
        grid.setItems(raceService.getAllRaces());
    }

    //private BookForm form = new BookForm(this);
    //    private BookService bookService = BookService.getInstance();
    //    private Grid<Book> grid = new Grid<>(Book.class);
    //    private TextField filter = new TextField();
    //    private Button addNewBook = new Button("Add new book");
    //    private Button redirectionTest = new Button("Redirection Test");
    //
    //    public MainView() {
    //        form.setBook(null);
    //        filter.setPlaceholder("Filter by title...");
    //        filter.setClearButtonVisible(true);
    //        filter.setValueChangeMode(ValueChangeMode.EAGER);
    //        filter.addValueChangeListener(e -> update());
    //
    //        addNewBook.addClickListener(e -> {
    //            grid.asSingleSelect().clear();
    //            form.setBook(new Book());
    //        });
    //
    //        HorizontalLayout toolBar = new HorizontalLayout(filter, addNewBook);
    //
    //        grid.setColumns("title", "author", "publicationYear", "type");
    //        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
    //        mainContent.setSizeFull();
    //        grid.setSizeFull();
    //
    //        redirectionTest.addClickListener(e ->
    //                redirectionTest.getUI().ifPresent(ui ->
    //                        ui.navigate("some/path"))
    //        );
    //
    //        add(toolBar, mainContent, redirectionTest);
    //        setSizeFull();
    //        refresh();
    //        grid.asSingleSelect().addValueChangeListener(event -> form.setBook(grid.asSingleSelect().getValue()));
    //    }
    //
    //    private void update(){
    //        grid.setItems(bookService.findByTitle(filter.getValue()));
    //    }
    //    public void refresh() {
    //        grid.setItems(bookService.getBooks());
    //    }
}
