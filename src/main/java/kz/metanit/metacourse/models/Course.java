package kz.metanit.metacourse.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String description;
    private int duration = 1;
    @ManyToMany
    private List<Category> categories = new ArrayList<>();
    private double rating;
    private int calculate = 0;
    @OneToMany
    private Collection<Module> modules = new ArrayList<>();
}
