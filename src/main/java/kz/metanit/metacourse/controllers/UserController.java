package kz.metanit.metacourse.controllers;

import kz.metanit.metacourse.dto.RegistrationDto;
import kz.metanit.metacourse.models.Person;
import kz.metanit.metacourse.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/{id}")
    public ResponseEntity<?> cabinet(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping("/api/{id}")
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

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(id);
    }
}
