package com.lessonprojectwithvaadin.demo.ui.component;

import com.lessonprojectwithvaadin.demo.dao.entity.Patient;
import com.lessonprojectwithvaadin.demo.service.implemention.PatientServiceImpl;
import com.sun.glass.ui.Window;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class PatientEditor extends VerticalLayout implements KeyNotifier {

    @Autowired
    private final PatientServiceImpl service;

    private Patient patient;

    private TextField fname = new TextField("First name");
    private TextField lname = new TextField("Last name");
    private TextField patronymic = new TextField("Middle name");
    private IntegerField phone = new IntegerField("Number phone");

    private Button save = new Button("save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("cancel");
    private Button delete = new Button("delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save,cancel,delete);

    private Binder<Patient> binder = new Binder<>(Patient.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler{
        void onChange();
    }

    public PatientEditor(PatientServiceImpl service){
        this.service = service;
        phone.setPlaceholder("enter number phone ");
        add(fname,lname,patronymic,phone,actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER,e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editPatient(patient));
        setVisible(false);
    }

    private void save(){
        service.save(patient);
        changeHandler.onChange();
    }
    private void delete(){
        service.delete(patient);
        changeHandler.onChange();
    }
    public void editPatient(Patient newPatient){
        if(newPatient == null){
            setVisible(false);
            return;
        }
        if (newPatient.getId() != null) {
            patient = service.getById(newPatient.getId());

            if(service.getById(newPatient.getId()) != null){
                patient = newPatient;
            }
        }else{
            patient = newPatient;
        }

        binder.setBean(patient);
        setVisible(true);
        lname.focus();
    }
}
