package com.lessonprojectwithvaadin.demo.dao.entity;

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
@ToString(of = {"id","fname","lname","patronymic","specialist"})
@EqualsAndHashCode(of = {"id","fname","lname","patronymic","specialist"})
@NoArgsConstructor
@Table(name = "doctor")
public class Doctor implements Serializable {

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

    @Column(name = "specialist")
    private String specialist;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Set<Recipe> recipes = new HashSet<>();


    public String toShow() {
        return lname + " " + fname.toUpperCase().charAt(0) + ". "+ patronymic.toUpperCase().charAt(0)+".";
    }

}
