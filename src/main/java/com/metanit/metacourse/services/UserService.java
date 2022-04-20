package com.metanit.metacourse.services;

import com.metanit.metacourse.models.Person;
import com.metanit.metacourse.models.Role;

import java.util.List;

public interface UserService {
    Person saveUser(Person user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    Person getUser(String email);
    List<Person> getUsers();
    Person getUser(Long id);
    void deleteUser(Long id);
    Person update(Long id, Person person);
}
