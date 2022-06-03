package kz.metanit.metacourse.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RegistrationDto {
    private String email;
    private String name;
    private String surname;
    private Date date;
    private String password;
}
