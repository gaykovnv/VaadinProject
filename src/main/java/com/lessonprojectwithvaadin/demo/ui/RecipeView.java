package com.lessonprojectwithvaadin.demo.ui;

import com.lessonprojectwithvaadin.demo.dao.entity.Priority;
import com.lessonprojectwithvaadin.demo.dao.entity.Recipe;
import com.lessonprojectwithvaadin.demo.service.implemention.RecipeServiceImpl;
import com.lessonprojectwithvaadin.demo.ui.component.RecipeEditor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.beans.factory.annotation.Autowired;

@Route("some/recipe")
public class RecipeView extends VerticalLayout {

    private final RecipeServiceImpl service;

    private final RecipeEditor editor;
    private final Grid<Recipe> grid = new Grid<>(Recipe.class);

    private final Button filter = new Button("filter");
    private final Button addRecipe = new Button("add recipe");
    private final Button urlDoctor = new Button("Doctors",VaadinIcon.AIRPLANE.create());
    private final Button urlPatient = new Button("Patients",VaadinIcon.AIRPLANE.create());
    private final HorizontalLayout toolBar = new HorizontalLayout(addRecipe,filter,urlDoctor,urlPatient);
    private final VerticalLayout panelFilter = new VerticalLayout();

    @Autowired
    public RecipeView(RecipeServiceImpl service,RecipeEditor editor){
        this.service = service;
        this.editor = editor;

        configureGrid();
        configureEditor();


        add(toolBar,panelFilter,grid,editor);

        urlPatient.addClickListener(event -> getUI().get().getPage().setLocation("some/patient"));
        urlDoctor.addClickListener(event ->getUI().get().getPage().setLocation("some/doctor"));

        filter.addClickListener(e ->{
            if(!panelFilter.isVisible()){
                panelFilter();
            }else{
                panelFilter.removeAll();
                panelFilter.setVisible(false);
            }

        });

        grid.asSingleSelect().addValueChangeListener(e ->editor.editRecipe(e.getValue()));


        addRecipe.addClickListener( e -> {
            System.out.println(editor.isVisible());
            //editor.setVisible(false);
            if (!editor.isVisible()) {
                editor.setVisible(true);
                editor.editRecipe(new Recipe());
            }else{
                editor.setVisible(false);
                editor.removeAll();
            }
        });

        showRecipe(" "," "," ");
    }

    private void showRecipe(String filterOne,String filterTwo,String filterThree){
        if(filterOne.isEmpty() && filterTwo.isEmpty()){
            grid.setItems(service.findAll());
        }else{
            grid.setItems(service.findByFilter(filterOne,filterTwo,filterThree));
        }

    }
//    private List<Recipe> toShow(){
//        List<Recipe> recipes = service.findAll();
//        for(int i = 0; i<recipes.size();i++){
//            recipes.get(i).getDoctor().toShow();
//            recipes.get(i).getPatient().toShow();
//        }
//        return recipes;
//    }
    private void panelFilter(){
        TextField filterDesc = new TextField("Description","enter any description");
        Select<Priority> filterPrio = new Select<>(Priority.NORM,Priority.CITO,Priority.STATIM);
        filterPrio.setPlaceholder("Priority... ");
        filterPrio.setLabel("Choose priority:");
        TextField filterPatient = new TextField("Lname patient: ","enter lname patient");
        Button apply = new Button("apply", VaadinIcon.CHECK.create());
        filterDesc.setValueChangeMode(ValueChangeMode.LAZY);

        apply.addClickListener(e -> {
            try {
                if (filterPrio.getValue().toString() == null){
                    filterPrio.setValue(Priority.valueOf(" "));
                }
                showRecipe(filterDesc.getValue(),filterPrio.getValue().toString(),filterPatient.getValue());
            }catch (Exception exception){
                exception.getMessage();
            }
            panelFilter.removeAll();
            panelFilter.setVisible(false);
        });

        Button cancel = new Button("cancel");
        cancel.addClickListener(e ->{
            showRecipe(" "," "," ");
            panelFilter.removeAll();
            panelFilter.setVisible(false);
        });

        HorizontalLayout buttons = new HorizontalLayout(apply,cancel);
        panelFilter.add(filterDesc,filterPrio,filterPatient,buttons);
        panelFilter.setVisible(true);
    }
    private void configureGrid(){
        grid.setWidth("50");
        grid.setHeight("50");
        grid.addClassName("recipe");
        grid.setColumns("description","patient","doctor","date","validity","priority");
    }
    private void configureEditor(){
        editor.setHeight("50");
        editor.setWidth("50");
    }
}
