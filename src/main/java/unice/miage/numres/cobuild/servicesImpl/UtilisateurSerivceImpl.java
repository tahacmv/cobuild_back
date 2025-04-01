package unice.miage.numres.cobuild.servicesImpl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Utilisateur;
import unice.miage.numres.cobuild.repository.UserRepository;
import unice.miage.numres.cobuild.services.UtilisateurService;

@Service
@RequiredArgsConstructor
public class UtilisateurSerivceImpl implements UtilisateurService {

    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public void uploadProfilePicture(String username, MultipartFile  file){
            Utilisateur user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            String fileName = fileStorageService.saveFile(file, "profiles");
            user.setProfilePictureUrl("/uploads/profiles/" + fileName);
            userRepository.save(user);
    }
    @Override
    public Utilisateur getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
