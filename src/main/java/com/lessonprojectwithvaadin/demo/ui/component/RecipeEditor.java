package com.lessonprojectwithvaadin.demo.ui.component;

import com.lessonprojectwithvaadin.demo.dao.entity.Doctor;
import com.lessonprojectwithvaadin.demo.dao.entity.Patient;
import com.lessonprojectwithvaadin.demo.dao.entity.Priority;
import com.lessonprojectwithvaadin.demo.dao.entity.Recipe;
import com.lessonprojectwithvaadin.demo.service.implemention.RecipeServiceImpl;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class RecipeEditor extends VerticalLayout implements KeyNotifier {

    @Autowired
    private final RecipeServiceImpl service;

    private Recipe recipe;
    private final TextField description = new TextField("Description: ","type of text");
    private final DatePicker date = new DatePicker("Date create: ");
    private final DatePicker validity = new DatePicker("Validity: ");
    private final Select<Priority> prioritySelect = new Select<>(Priority.NORM,Priority.CITO,Priority.STATIM);
    private final ComboBox<Doctor> doctor = new ComboBox<>("Doctor: ");
    private final ComboBox<Patient> patient = new ComboBox<>("Patient: ");

    private final Button save = new Button("save", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("cancel");
    private final Button delete = new Button("delete",VaadinIcon.TRASH.create());
    private final HorizontalLayout buttons = new HorizontalLayout(save,cancel,delete);

    private final Binder<Recipe> binder = new Binder<>(Recipe.class);

    public RecipeEditor(RecipeServiceImpl service){
        this.service = service;

        prioritySelect.setLabel("Choose priority");
        prioritySelect.setPlaceholder("---press---");

        binder.bindInstanceFields(this);
        doctor.setItems(service.findAllDoctors());
        doctor.setItemLabelGenerator(Doctor::toShow);

        patient.setItems(service.findAllPatients());
        patient.setItemLabelGenerator(Patient::toShow);

        add(description,date,validity,prioritySelect,doctor,patient,buttons);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editRecipe(recipe));
        setVisible(false);
    }
    private void save(){
        setVisible(false);
        recipe.setPriority(prioritySelect.getValue());
        service.saveRecipe(recipe);
        UI ui = new UI();
        ui.getUI().get().getPage().reload();

    }
    private void delete(){
        if(recipe ==null){
            setVisible(false);
            return;
        }
        setVisible(false);
        service.deleteRecipe(recipe);
  }
    public void editRecipe(Recipe newRecipe){

        if(newRecipe == null){
            setVisible(false);
            return;
        }
        if (newRecipe.getId() != null){

            recipe = service.getOneById(newRecipe.getId());
            if (service.getOneById(newRecipe.getId()) != null){
                recipe = newRecipe;
            }
        }else{

            recipe = newRecipe;
        }

        binder.setBean(recipe);
        setVisible(true);
        doctor.focus();
    }

}
