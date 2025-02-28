
package com.example.auth.controller;

import com.example.auth.dto.LoginResponseDTO;
import com.example.auth.dto.UserDTO;
import com.example.auth.entity.User;
import com.example.auth.service.AuthenticationService;
import com.example.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserDTO userDto) {
        User registeredUser = authenticationService.signup(userDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody UserDTO userDto) {
        User authenticatedUser = authenticationService.authenticate(userDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDTO loginResponse = new LoginResponseDTO();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/remainingTime")
    public ResponseEntity<String> getRemainingTime(@RequestParam("token") String token) {
        if (jwtService.isTokenExpired(token)) {
            return ResponseEntity.ok().body("Token has expired");
        }

        return ResponseEntity.ok().body("Expiration: " + jwtService.getRemainingTime(token));
    }
}