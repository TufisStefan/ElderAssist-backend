package com.example.loginexample.controllerTest;

import com.example.loginexample.controller.AuthController;
import com.example.loginexample.model.ERole;
import com.example.loginexample.model.Role;
import com.example.loginexample.model.User;
import com.example.loginexample.payload.request.SignupRequest;
import com.example.loginexample.repository.RoleRepository;
import com.example.loginexample.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    JacksonTester<SignupRequest> requestJson;
    private MockMvc mockMvc;

    @Mock
    PasswordEncoder encoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }

    @Test
    void registerUser_WhenUsernameExists_ReturnBadRequest() throws Exception {
        when(userRepository.existsByUsername(any(String.class))).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(
                post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(
                        requestJson.write(new SignupRequest("user", "user@gmail.com", "password", null)).getJson()
                )).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"message\":\"Error: Username is already taken.\"}", response.getContentAsString());
    }
    @Test
    void registerUser_WhenEmailExists_ReturnBadRequest() throws Exception {
        when(userRepository.existsByEmail(any(String.class))).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(
                post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(
                        requestJson.write(new SignupRequest("user", "user@gmail.com", "password", null)).getJson()
                )).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"message\":\"Error: Email is already in use.\"}", response.getContentAsString());
    }
    @Test
    void registerUser_GivenValidUser_ReturnOkResponse() throws Exception {
        Optional<Role> optionalRole = Optional.of(new Role());
        when(roleRepository.findByName(any(ERole.class))).thenReturn(optionalRole);
        when(encoder.encode(any(String.class))).thenAnswer(i -> i.getArgument(0));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        MockHttpServletResponse response = mockMvc.perform(
                post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(
                        requestJson.write(new SignupRequest("user", "user@gmail.com", "password", null)).getJson()
                )).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
