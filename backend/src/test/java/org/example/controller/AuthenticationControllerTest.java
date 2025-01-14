package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dtos.LoginUserDto;
import org.example.dtos.RegisterUserDto;
import org.example.model.User;
import org.example.responses.LoginResponse;
import org.example.service.AuthenticationService;
import org.example.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthenticationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "password");
        User user = new User();
        user.setUserID(1L);

        when(authenticationService.signup(registerUserDto)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerUserDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testRegisterUser_Failure() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "password");

        when(authenticationService.signup(registerUserDto)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerUserDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to register user: "));
    }

    @Test
    void testRegisterUser_Exception() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "password");

        when(authenticationService.signup(registerUserDto)).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerUserDto)))
                .andExpect(status().isInternalServerError());
    }

}