package unice.miage.numres.cobuild.util;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Role;
import unice.miage.numres.cobuild.model.User;
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

    @Override
    public void run(String... args) {
        // Ensure roles exist
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");

        // Create default Admin user
        createUserIfNotExists("admin", "admin", "ADMIN");

        // Create default User
        createUserIfNotExists("user", "user", "USER");

        System.out.println("Database has been seeded successfully!");
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            return roleRepository.save(role);
        });
    }

    private void createUserIfNotExists(String username, String password, String roleName) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));

            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

            user.setRoles(Collections.singleton(role));
            userRepository.save(user);

            System.out.println("Created user: " + username + " with role: " + roleName);
        }
    }
}
