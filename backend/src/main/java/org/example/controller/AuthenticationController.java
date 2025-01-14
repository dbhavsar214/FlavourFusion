package org.example.controller;

import org.example.dtos.LoginUserDto;
import org.example.dtos.RegisterUserDto;
import org.example.model.User;
import org.example.responses.LoginResponse;
import org.example.service.AuthenticationService;
import org.example.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://csci5308-vm2.research.cs.dal.ca:3000", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
        try{
            User registeredUser = authenticationService.signup(registerUserDto);
            if (registeredUser != null) {
                return ResponseEntity.ok(registeredUser);
            }
            else{
                String errorMessage = "Failed to register user: ";
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
            }

        }catch (Exception e){
            String errorMessage = "Failed to register user: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        long userID = authenticatedUser.getUserID();
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(), userID);

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/check")
    public String check(){
        return "Application working!!";
    }
}
