package com.lessonprojectwithvaadin.demo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(of = {"fname", "lname", "patronymic", "phone"})
@EqualsAndHashCode(of = {"id", "fname", "lname", "patronymic", "phone"})
@NoArgsConstructor
@Table(name = "patient")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String fname;

    @Column(name = "last_name")
    private String lname;

    @Column(name = "middle_name")
    private String patronymic;

    @Column(name = "phone")
    private int phone;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<Recipe> recipes;


    public String toShow() {
        return lname + " " + fname.toUpperCase().charAt(0) + ". " + patronymic.toUpperCase().charAt(0) + ".";
    }
}
