package dev.niashi.library_management_backend.controller;

import dev.niashi.library_management_backend.model.Loan;
import dev.niashi.library_management_backend.model.User;
import dev.niashi.library_management_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = service.getUserById(id);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();

        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = service.addUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserById(
            @PathVariable Long id,
            @RequestBody Map<String, Object> newInfo
    ) {
        User updateduser = service.updateUserById(id, newInfo);

        return ResponseEntity.ok().body(updateduser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        try {
            service.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
