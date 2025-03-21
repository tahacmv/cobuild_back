package unice.miage.numres.cobuild.servicesImpl;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Utilisateur;
import unice.miage.numres.cobuild.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

        private final UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Utilisateur user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur not found: " + username));

                return org.springframework.security.core.userdetails.User
                                .withUsername(user.getUsername())
                                .password(user.getPassword())
                                .roles(user.getRoles().stream().map(r -> r.getName().toUpperCase())
                                                .collect(Collectors.toList())
                                                .toArray(new String[0]))
                                .build();
        }
}
