package com.example.loginexample.serviceTest;


import com.example.loginexample.model.User;
import com.example.loginexample.repository.UserRepository;
import com.example.loginexample.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void initService() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsername_WhenGivenValidUsername_ReturnUser() {
        Optional<User> user = Optional.of(new User("user", "user@gmail.com", "password"));
        doReturn(user).when(userRepository).findByUsername("user");
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getUsername());
        assertThat(userDetails).isNotNull();
        assertEquals(user.get().getUsername(), userDetails.getUsername());
        assertEquals(user.get().getPassword(), userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_WhenUsernameNotFound_ThrowException() {
        Optional<User> user = Optional.of(new User("user", "user@gmail.com", "password"));
        doReturn(Optional.empty()).when(userRepository).findByUsername("user");
        Throwable exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("user"));
        assertEquals("User user Not Found", exception.getMessage());
    }
}
