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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
    void userReturnedWhenGivenValidUsername() {
        Optional<User> user = Optional.of(new User("user", "user@gmail.com", "password"));
        doReturn(user).when(userRepository).findByUsername(anyString());
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getUsername());
        assertThat(userDetails).isNotNull();
        assertEquals(user.get().getUsername(), userDetails.getUsername());
        assertEquals(user.get().getPassword(), userDetails.getPassword());
    }
}
