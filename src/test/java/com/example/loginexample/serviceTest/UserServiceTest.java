package com.example.loginexample.serviceTest;

import com.example.loginexample.model.ERole;
import com.example.loginexample.model.Role;
import com.example.loginexample.model.User;
import com.example.loginexample.repository.RoleRepository;
import com.example.loginexample.repository.UserRepository;
import com.example.loginexample.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    UserService userService;

    @BeforeEach
    void initService() {
        userService = new UserService(userRepository, roleRepository);
    }

    @Test
    void findUserByName_WhenGivenValidUsername_ReturnUser() {
        Optional<User> user = Optional.of(new User("user", "user@gmail.com", "password"));
        when(userRepository.findByUsername("user")).thenReturn(user);

        User result = userService.findUserByName(user.get().getUsername());
        assertEquals(user.get().getUsername(), result.getUsername());
    }

    @Test
    void findUserByName_WhenNotFound_ReturnNull() {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        User result = userService.findUserByName("");
        assertNull(result);
    }

    @Test
    void deleteUser_WhenUserExists_DeleteUser() {
        Optional<User> user = Optional.of(new User("user", "user@gmail.com", "password"));
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        doAnswer(invocationOnMock -> {
            assertEquals(user.get(), invocationOnMock.getArgument(0));
            return null;
        }).when(userRepository).delete(user.get());
        userService.deleteUser(user.get().getUsername());
    }

    @Test
    void deleteUser_WhenUserNotFound_DoNothing() {
        Optional<User> user = Optional.of(new User("user", "user@gmail.com", "password"));
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        lenient().doAnswer(invocationOnMock -> {
            fail();
            return null;
        }).when(userRepository).delete(user.get());
        userService.deleteUser(user.get().getUsername());
    }

    @Test
    void getUsersByRole_WhenGivenValidRole_ReturnUserPage() {
        ERole role = ERole.ROLE_USER;
        int pageNumber = 1;
        int pageSize = 5;
        User user = new User("user", "user@gmail.com", "password");
        Page<User> userPage = new PageImpl<User>(List.of(user));
        when(roleRepository.findByName(any())).thenAnswer(invocationOnMock -> {
            Optional<Role> optionalRole = Optional.of(new Role());
            optionalRole.get().setName(invocationOnMock.getArgument(0));
            return optionalRole;
        });
        when(userRepository.findAllByRolesContains(any(),any())).thenReturn(userPage);
        Page<User> result = userService.getUsersByRole(role, pageNumber, pageSize);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getUsersByRole_WhenGivenInvalidRole_ReturnNull() {
        ERole role = ERole.ROLE_USER;
        int pageNumber = 1;
        int pageSize = 5;
        User user = new User("user", "user@gmail.com", "password");
        Page<User> userPage = new PageImpl<User>(List.of(user));
        when(roleRepository.findByName(any())).thenReturn(Optional.empty());
        Page<User> result = userService.getUsersByRole(role, pageNumber, pageSize);
        assertNull(result);
    }

    @Test
    void updateUser_WhenGivenExistentUser_ReturnUpdatedUser() {
        User user = new User("user", "user@gmail.com", "password");
        user.setId(1L);
        when(userRepository.existsById(any())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        User result = userService.updateUser(user.getId(), user);
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void updateUser_WhenGivenInexistentUser_ReturnUser() {
        User user = new User("user", "user@gmail.com", "password");
        user.setId(1L);
        when(userRepository.existsById(any())).thenReturn(false);
        User result = userService.updateUser(user.getId(), user);
        assertEquals(user, result);
    }
}
