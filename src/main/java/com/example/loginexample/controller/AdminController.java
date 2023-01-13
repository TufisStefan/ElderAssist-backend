package com.example.loginexample.controller;

import com.example.loginexample.model.ERole;
import com.example.loginexample.model.User;
import com.example.loginexample.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getUsers(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return userService.getUsersByRole(ERole.ROLE_USER, pageNumber, pageSize);
    }

    @DeleteMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@RequestParam String username) {
        userService.deleteUser(username);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@RequestParam long id, @RequestBody String jsonObject) {
        ResponseEntity<User> response;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(jsonObject, User.class);
            response = new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Username or email already taken", HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
