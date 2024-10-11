package dev.niashi.library_management_backend.controller;

import dev.niashi.library_management_backend.model.Book;
import dev.niashi.library_management_backend.model.Loan;
import dev.niashi.library_management_backend.model.User;
import dev.niashi.library_management_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(description = "Finds a user by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "Id to be searched.", required = true)
            @PathVariable Long id
    ) {
        try {
            User user = service.getUserById(id);
            return ResponseEntity.ok().body(user);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(description = "Finds all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully.")
    })
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();

        return ResponseEntity.ok().body(users);
    }

    @Operation(description = "Adds a new user.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User added successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input.")
    })
    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User savedUser = service.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(description = "Updates a user by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "400", description = "Invalid input.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserById(
            @PathVariable Long id,
            @RequestBody Map<String, Object> newInfo
    ) {
        try {
            User updateduser = service.updateUserById(id, newInfo);
            return ResponseEntity.status(HttpStatus.OK).body(updateduser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(description = "Deletes a user by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        try {
            service.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
