package com.metanit.metacourse.services;

import com.metanit.metacourse.models.Course;
import com.metanit.metacourse.models.Person;
import com.metanit.metacourse.models.Role;
import com.metanit.metacourse.repositories.RoleRepository;
import com.metanit.metacourse.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserImplementation implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Person saveUser(Person user) {
        log.info("Saving new User {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role by name {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        Person user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Person getUser(String email) {
        log.info("Get user by email {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Person> getUsers() {
        log.info("Get users");
        return userRepository.findAll();
    }

    @Override
    public Person getUser(Long id) {
        log.info("Get user by id {}", id);
        Optional<Person> person = userRepository.findById(id);
        return person.orElseGet(Person::new);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Delete user by id {}", id);
        Person person = getUser(id);
        userRepository.delete(person);
    }

    @Override
    public Person update(Long id, Person person) {
        log.info("Update user by id {} ", id);
        Person personOld = getUser(id);
        for (Role role : personOld.getRoles()) {
            person.getRoles().add(role);
        }
        for (Course course : personOld.getCoursesCreated()) {
            person.getCoursesCreated().add(course);
        }
        for (Course course : personOld.getCoursesTaken()) {
            person.getCoursesTaken().add(course);
        }
        deleteUser(id);
        saveUser(person);
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found {}", user.getEmail());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
