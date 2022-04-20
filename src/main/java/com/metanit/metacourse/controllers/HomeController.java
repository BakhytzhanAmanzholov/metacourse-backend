package com.metanit.metacourse.controllers;

import com.metanit.metacourse.dto.LoginDto;
import com.metanit.metacourse.dto.RegistrationDto;
import com.metanit.metacourse.models.Person;
import com.metanit.metacourse.services.UserService;
import com.metanit.metacourse.token.JWTAuthResponse;
import com.metanit.metacourse.token.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil tokenProvider;

    @PostMapping("/api/signin")
    public ResponseEntity<PersonToken> authenticateUser(@RequestBody LoginDto loginDto) {
        Person person = userService.getUser(loginDto.getEmail());
        if (person == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        PersonToken personToken = new PersonToken(new JWTAuthResponse(token), person);
        return ResponseEntity.ok(personToken);
    }

    @PostMapping("/api/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto) {
        if (userService.getUser(registrationDto.getEmail()) != null) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        Person person = new Person();
        person.setEmail(registrationDto.getEmail());
        person.setName(registrationDto.getName());
        person.setSurname(registrationDto.getSurname());
        person.setDateOfBirth(registrationDto.getDate());
        person.setPassword(registrationDto.getPassword());
        userService.saveUser(person);
        userService.addRoleToUser(person.getEmail(), "ROLE_USER"); // TODO: перенести в service
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}

@Data
class PersonToken {
    private JWTAuthResponse jwtAuthResponse;
    private Person person;

    public PersonToken(JWTAuthResponse jwtAuthResponse, Person person) {
        this.jwtAuthResponse = jwtAuthResponse;
        this.person = person;
    }
}
