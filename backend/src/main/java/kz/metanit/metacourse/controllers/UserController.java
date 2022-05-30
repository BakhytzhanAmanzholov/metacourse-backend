package kz.metanit.metacourse.controllers;

import kz.metanit.metacourse.dto.RegistrationDto;
import kz.metanit.metacourse.models.Course;
import kz.metanit.metacourse.models.Person;
import kz.metanit.metacourse.services.CourseService;
import kz.metanit.metacourse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<?> cabinet(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody RegistrationDto registrationDto) {
        Person person = userService.getUser(id);
        if (userService.getUser(registrationDto.getEmail()) != null &&
                userService.getUser(registrationDto.getEmail()) != person) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (person == null) {
            return new ResponseEntity<>("HttpStatus.BAD_REQUEST", HttpStatus.OK);
        }
        Person personUpdate = new Person();
        personUpdate.setEmail(registrationDto.getEmail());
        personUpdate.setName(registrationDto.getName());
        personUpdate.setSurname(registrationDto.getSurname());
        personUpdate.setDateOfBirth(registrationDto.getDate());
        personUpdate.setPassword(registrationDto.getPassword());
        userService.update(id, personUpdate); // TODO: delete update password and email
        return new ResponseEntity<>("User update successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/take/{id}")
    public ResponseEntity<?> takeCourse(@PathVariable Long id){
        Course course = courseService.getCourse(id);
        Person person = userService.getUser(isLogged());
        userService.addCourseTakenToUser(course, person);
        return new ResponseEntity<>("Course take successfully", HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> completeCourse(@PathVariable Long id){
        Course course = courseService.getCourse(id);
        Person person = userService.getUser(isLogged());
        userService.completeCourse(course, person);
        return new ResponseEntity<>("Course complete successfully", HttpStatus.OK);
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
