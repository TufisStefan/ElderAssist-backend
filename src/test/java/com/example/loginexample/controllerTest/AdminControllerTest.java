package com.example.loginexample.controllerTest;

import com.example.loginexample.controller.AdminController;
import com.example.loginexample.model.ERole;
import com.example.loginexample.model.User;
import com.example.loginexample.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @Mock
    UserService userService;

    @InjectMocks
    private AdminController adminController;


    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .build();
    }

    @Test
    void getUsers_WhenCalled_ReturnAllUsers() throws Exception {
        int pageNumber = 0;
        int pageSize = 5;
        List<User> userList = List.of(new User("user", "user@gmail.com", "password"));
        when(userService.getUsersByRole(any(ERole.class), any(Integer.class), any(Integer.class))).thenReturn(new PageImpl<>(userList));
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/test/admin")
                                .param("pageNumber", String.valueOf(pageNumber))
                                .param("pageSize", String.valueOf(pageSize)))
                .andReturn().getResponse();
        assertTrue(response.getContentAsString().contains("user@gmail.com"));
    }


}
