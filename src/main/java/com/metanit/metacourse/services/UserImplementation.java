package com.metanit.metacourse.services;

import com.metanit.metacourse.models.Person;
import com.metanit.metacourse.models.Role;
import com.metanit.metacourse.repositories.RoleRepositories;
import com.metanit.metacourse.repositories.UserRepositories;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserImplementation  implements UserService, UserDetailsService {
    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Person saveUser(Person user) {
        log.info("Saving new User {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepositories.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role by name {}", role.getName());
        return roleRepositories.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        Person user = userRepositories.findByEmail(email);
        Role role = roleRepositories.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Person getUser(String email) {
        log.info("Get user by email {}", email);
        return userRepositories.findByEmail(email);
    }

    @Override
    public List<Person> getUsers() {
        log.info("Get users");
        return userRepositories.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = userRepositories.findByEmail(email);
        if(user == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        else {
            log.info("User found {}", user.getEmail());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
