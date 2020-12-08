package com.lessonprojectwithvaadin.demo.ui;

import com.lessonprojectwithvaadin.demo.dao.entity.Patient;
import com.lessonprojectwithvaadin.demo.service.implemention.PatientServiceImpl;
import com.lessonprojectwithvaadin.demo.ui.component.PatientEditor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "some/patient")
public class PatientView extends VerticalLayout {

    private final PatientServiceImpl service;

    private Grid<Patient> grid = new Grid<>(Patient.class);
    private final TextField filter = new TextField(" ", "Type to filter");
    private final Button addNewBtn = new Button("Add patient");
    private final Button urlDoctor = new Button("Doctors", VaadinIcon.AIRPLANE.create());
    private final Button urlRecipe = new Button("Recipes", VaadinIcon.AIRPLANE.create());
    private final HorizontalLayout toolBar = new HorizontalLayout(filter, addNewBtn, urlDoctor, urlRecipe);

    private final PatientEditor editor;

    @Autowired
    public PatientView(PatientServiceImpl service, PatientEditor editor) {
        this.service = service;
        this.editor = editor;
        configureGrid();
        configureEditor();

        add(toolBar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showPatient(e.getValue()));

        urlDoctor.addClickListener(event -> getUI().get().getPage().setLocation("some/doctor"));
        urlRecipe.addClickListener(event -> getUI().get().getPage().setLocation("some/recipe"));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editPatient(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editPatient(new Patient()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showPatient(filter.getValue());
        });

        showPatient(" ");
    }

    private void showPatient(String filter) {
        if (filter.isEmpty()) {
            grid.setItems(service.findAll());
        } else {
            grid.setItems(service.filter(filter));
        }
    }

    private void configureEditor() {
        grid.setHeight("50");
        grid.setWidth("50");
    }

    private void configureGrid() {
        grid.addClassName("patient");
        grid.setHeight("50");
        grid.setWidth("50");
        grid.setColumns("id", "fname", "lname", "patronymic", "phone");

    }
}


