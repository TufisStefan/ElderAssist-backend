package com.example.loginexample.controller;

import com.example.loginexample.model.ERole;
import com.example.loginexample.model.User;
import com.example.loginexample.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    private UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getUsers(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return userService.getUsersByRole(ERole.ROLE_USER, pageNumber, pageSize);
    }
}
