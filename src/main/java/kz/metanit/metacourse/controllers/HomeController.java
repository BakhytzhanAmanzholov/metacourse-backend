package kz.metanit.metacourse.controllers;

import kz.metanit.metacourse.dto.LoginDto;
import kz.metanit.metacourse.dto.RegistrationDto;
import kz.metanit.metacourse.models.Course;
import kz.metanit.metacourse.models.Person;
import kz.metanit.metacourse.services.CourseService;
import kz.metanit.metacourse.services.UserService;
import kz.metanit.metacourse.token.JWTAuthResponse;
import kz.metanit.metacourse.token.JwtUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil tokenProvider;
    private final CourseService courseService;

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
    @GetMapping("/")
    public ResponseEntity<?> index(){
        List<Course> recommendationsCourses = courseService.topCourses();
        Person user = userService.getUser(isLogged());
        List<Course> categoriesCourses = courseService.categoriesCourses(user);
        Recommendations recommendations = new Recommendations(recommendationsCourses, categoriesCourses);
        return ResponseEntity.ok(recommendations);
    }
    public String isLogged(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        log.info(currentPrincipalName);
        if(!currentPrincipalName.equals("anonymousUser")){
            return currentPrincipalName;
        }
        return "anonymousUser";
    }
}

@Data
class Recommendations{
    private List<Course> recommendationsCourses ;
    private List<Course> categoriesCourses;

    public Recommendations(List<Course> recommendationsCourses, List<Course> categoriesCourses) {
        this.recommendationsCourses = recommendationsCourses;
        this.categoriesCourses = categoriesCourses;
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
