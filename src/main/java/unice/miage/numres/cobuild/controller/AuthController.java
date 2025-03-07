package unice.miage.numres.cobuild.controller;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.requestModel.LoginRequestModel;
import unice.miage.numres.cobuild.requestModel.RegisterRequestModel;
import unice.miage.numres.cobuild.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestModel request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestModel request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
