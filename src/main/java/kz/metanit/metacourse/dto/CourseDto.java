package kz.metanit.metacourse.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseDto {
    private String title;
    private String description;
    private int duration;
    private List<String> categories;
}
