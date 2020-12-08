package com.lessonprojectwithvaadin.demo.ui;

import com.lessonprojectwithvaadin.demo.dao.entity.Doctor;
import com.lessonprojectwithvaadin.demo.service.implemention.DoctorServiceImpl;
import com.lessonprojectwithvaadin.demo.ui.component.DoctorEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("some/doctor")
public class DoctorView extends VerticalLayout {

    private final DoctorServiceImpl service;

    private final Grid<Doctor> grid = new Grid<>(Doctor.class);
    private final TextField filter = new TextField(" ", " Type to filter");

    private final DoctorEditor editor;
    private final Button addNewDoctor = new Button("add Doctor");
    private final Button information = new Button("information");
    private final Button urlPatient = new Button("Patients", VaadinIcon.AIRPLANE.create());
    private final Button urlRecipe = new Button("Recipes",VaadinIcon.AIRPLANE.create());
    private final HorizontalLayout toolBar =
            new HorizontalLayout(filter,addNewDoctor, information,urlPatient,urlRecipe);
    private final VerticalLayout staticInfo = new VerticalLayout();

    private final Button cancelInfo = new Button("cancel");

    @Autowired
    public DoctorView(DoctorServiceImpl service,DoctorEditor editor){
        this.service = service;
        this.editor = editor;

        configureGrid();
        configureEditor();
        staticInfo.setVisible(false);
        add(toolBar,staticInfo,grid,editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e ->showDoctors(e.getValue()));

        urlPatient.addClickListener(event -> getUI().get().getPage().setLocation("some/patient"));
        urlRecipe.addClickListener(event -> getUI().get().getPage().setLocation("some/recipe"));

        information.addClickListener(e -> {
            System.out.println(staticInfo.isVisible());
            if(!staticInfo.isVisible()){
                staticInfo();
                hiddenInfo();

            }else{
                staticInfo.removeAll();
                staticInfo.setVisible(false);
            }

        });

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editDoctor(e.getValue());
        });

        addNewDoctor.addClickListener(e -> editor.editDoctor(new Doctor()));

        editor.setChangeHandler(() ->{
            editor.setVisible(false);
            showDoctors(filter.getValue());
        });

        showDoctors(" ");
    }

    private void staticInfo(){
        Label amountDoctors = new Label("Doctor: "+String.valueOf(service.amountDoctors()));
        Label amountPatients = new Label("Patient: "+String.valueOf(service.amountPatients()));
        Label amountRecipes = new Label("Recipe: "+String.valueOf(service.amountRecipes()));

        staticInfo.add(amountDoctors,amountPatients,amountRecipes,cancelInfo);
        staticInfo.setVisible(true);

    }

    private void hiddenInfo(){
        cancelInfo.addClickListener(e ->staticInfo.removeAll());
    }

    private void showDoctors(String filter){
        if(filter.isEmpty()){
            grid.setItems(service.findAll());
        }else{
            grid.setItems(service.findByName(filter));
        }
    }
    private void configureEditor(){
        editor.setHeight("50");
        editor.setWidth("50");
    }

    private void configureGrid(){
        grid.setHeight("50");
        grid.setWidth("50");
        grid.addClassName("doctor");
        grid.setColumns("id","fname","lname","patronymic","specialist");
    }
}
