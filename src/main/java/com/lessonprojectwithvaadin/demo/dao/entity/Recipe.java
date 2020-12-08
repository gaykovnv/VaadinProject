package com.lessonprojectwithvaadin.demo.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id","description","patient","doctor","date","validity","priority"})
@ToString(of = {"description","patient","doctor","date","validity","priority"})
@NoArgsConstructor
@Table(name = "recipe")
public class Recipe implements Serializable,Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient ;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "date_create")
    private LocalDate date;

    @Column(name = "validity")
    private LocalDate validity;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

}
