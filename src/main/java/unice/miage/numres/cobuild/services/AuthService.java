package unice.miage.numres.cobuild.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Role;
import unice.miage.numres.cobuild.model.User;
import unice.miage.numres.cobuild.repository.RoleRepository;
import unice.miage.numres.cobuild.repository.UserRepository;
import unice.miage.numres.cobuild.requestModel.LoginRequestModel;
import unice.miage.numres.cobuild.requestModel.RegisterRequestModel;
import unice.miage.numres.cobuild.util.JwtUtil;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequestModel request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleRepository.findByName("USER").orElseThrow();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(LoginRequestModel request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getUsername());
    }
}