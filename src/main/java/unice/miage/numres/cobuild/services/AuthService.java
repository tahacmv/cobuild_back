package unice.miage.numres.cobuild.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Fournisseur;
import unice.miage.numres.cobuild.model.PorteurDeProjet;
import unice.miage.numres.cobuild.model.Role;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.model.Utilisateur;
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
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        Utilisateur user;

        switch (request.getRoleName().toUpperCase()) {
            case "FOURNISSEUR":
                user = new Fournisseur();
                break;
            case "PORTEURDEPROJET":
                user = new PorteurDeProjet();
                break;
            case "TRAVAILLEUR":
                user = new Travailleur();
                break;
            default:
                throw new RuntimeException("Invalid role: " + request.getRoleName());
        }

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        Role role = roleRepository.findByName(request.getRoleName().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return "Utilisateur registered successfully!";
    }

    public String login(LoginRequestModel request) {
        Utilisateur user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtil.generateToken(user.getUsername());
    }
}