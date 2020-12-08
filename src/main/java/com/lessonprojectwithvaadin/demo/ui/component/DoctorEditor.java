package com.lessonprojectwithvaadin.demo.ui.component;

import com.lessonprojectwithvaadin.demo.dao.entity.Doctor;
import com.lessonprojectwithvaadin.demo.service.implemention.DoctorServiceImpl;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class DoctorEditor extends VerticalLayout implements KeyNotifier {

    @Autowired
    private final DoctorServiceImpl service;

    private final TextField fname = new TextField("First name");
    private final TextField lname = new TextField("Last name");
    private final TextField patronymic = new TextField("Middle name");
    private final TextField specialist = new TextField("Specialist");

    private final Button save = new Button("save",VaadinIcon.CHECK.create());
    private final Button cancel = new Button("cancel");
    private final Button delete = new Button("delete", VaadinIcon.TRASH.create());
    private final HorizontalLayout buttons = new HorizontalLayout(save,cancel,delete);

    private Doctor doctor;

    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler{
        void onChange();
    }

    public DoctorEditor(DoctorServiceImpl service){
        this.service = service;

        add(fname,lname,patronymic,specialist,buttons);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        cancel.addClickListener(e -> editDoctor(doctor));
        delete.addClickListener(e -> delete());

        setVisible(false);
    }

    private void save(){
        service.save(doctor);
        changeHandler.onChange();
    }
    private void delete(){
        service.delete(doctor);
        changeHandler.onChange();
    }
    public void editDoctor(Doctor newDoctor){
        if(newDoctor == null){
            setVisible(false);
            return;
        }
        if (newDoctor.getId() != null){
            doctor = service.getOneById(newDoctor.getId());

            if (service.getOneById(newDoctor.getId()) != null){
                doctor = newDoctor;
            }
        }else{
            doctor = newDoctor;
        }

        binder.setBean(doctor);
        setVisible(true);
        lname.focus();
    }
}
