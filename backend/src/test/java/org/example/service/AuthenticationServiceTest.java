package org.example.service;

import static org.junit.jupiter.api.Assertions.*;
import org.example.dtos.LoginUserDto;
import org.example.dtos.RegisterUserDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setFirstName("John");
        registerUserDto.setLastName("Doe");
        registerUserDto.setEmail("john.doe@example.com");
        registerUserDto.setPassword("password123");
        registerUserDto.setBio("Bio");

        User user = new User("John Doe", "john.doe@example.com", "encodedPassword", "Bio");

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = authenticationService.signup(registerUserDto);
    }

    @Test
    void testAuthenticate_Success() {
        // Arrange
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("john.doe@example.com");
        loginUserDto.setPassword("password123");

        User user = new User("John Doe", "john.doe@example.com", "encodedPassword", "Bio");

        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        // Act
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Assert
        assertNotNull(authenticatedUser);
        assertEquals("John Doe", authenticatedUser.getUserName());
        assertEquals("john.doe@example.com", authenticatedUser.getEmailAddress());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("john.doe@example.com");
    }

    @Test
    void testAuthenticate_Failure() {
        // Arrange
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("john.doe@example.com");
        loginUserDto.setPassword("password123");

        doThrow(new BadCredentialsException("Invalid credentials")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(loginUserDto));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByEmail(anyString());
    }
}