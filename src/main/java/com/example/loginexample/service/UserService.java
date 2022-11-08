package com.example.loginexample.service;

import com.example.loginexample.model.ERole;
import com.example.loginexample.model.Role;
import com.example.loginexample.model.User;
import com.example.loginexample.repository.RoleRepository;
import com.example.loginexample.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public Page<User> getUsersByRole(ERole eRole, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Role role = roleRepository.findByName(eRole).orElse(null);
        return userRepository.findAllByRoles(role, page);
    }
}
