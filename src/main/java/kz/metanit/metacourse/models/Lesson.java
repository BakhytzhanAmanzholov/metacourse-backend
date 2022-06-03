package kz.metanit.metacourse.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String description;
    @OneToMany
    private Collection<Text> texts = new ArrayList<>();
    @OneToMany
    private Collection<Video> videos = new ArrayList<>();
    @OneToMany
    private Collection<Image> images = new ArrayList<>();
}
