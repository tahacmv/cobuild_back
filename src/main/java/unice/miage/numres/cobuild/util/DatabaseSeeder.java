package unice.miage.numres.cobuild.util;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Administrateur;
import unice.miage.numres.cobuild.model.Fournisseur;
import unice.miage.numres.cobuild.model.PorteurDeProjet;
import unice.miage.numres.cobuild.model.Role;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.model.Utilisateur;
import unice.miage.numres.cobuild.repository.RoleRepository;
import unice.miage.numres.cobuild.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            return roleRepository.save(role);
        });
    }

    private void createUserIfNotExists(String username, String password, String email, String roleName) {
        if (userRepository.findByUsername(username).isEmpty()) {
            Utilisateur user;

            switch (roleName) {
                case "ADMIN":
                    user = new Administrateur();
                    break;
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
                    user = new Utilisateur(); // Default for generic users
            }

            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

            user.setRoles(Collections.singleton(role));
            userRepository.save(user);

            System.out.println("Created user: " + username + " with role: " + roleName);
        }
    }

    @Override
    public void run(String... args) {
        // Ensure roles exist
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");
        createRoleIfNotExists("FOURNISSEUR");
        createRoleIfNotExists("PORTEURDEPROJET");
        createRoleIfNotExists("TRAVAILLEUR");

        // Create default Admin user
        createUserIfNotExists("admin", "admin", "admin@mail.com", "ADMIN");

        // Create default Utilisateur
        createUserIfNotExists("user", "user", "user@mail.com", "USER");

        // Create default Travailleur user
        createUserIfNotExists("travailleur", "travailleur", "travailleur@mail.com", "TRAVAILLEUR");

        // Create default Fournisseur user
        createUserIfNotExists("fournisseur", "fournisseur", "fournisseur@mail.com", "FOURNISSEUR");

        // Create default PorteurDeProjet user
        createUserIfNotExists("porteur", "porteur", "porteur@mail.com", "PORTEURDEPROJET");

        System.out.println("Database has been seeded successfully!");
    }

}
