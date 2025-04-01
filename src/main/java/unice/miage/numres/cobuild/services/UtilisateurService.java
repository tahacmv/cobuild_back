package unice.miage.numres.cobuild.services;

import org.springframework.web.multipart.MultipartFile;

import unice.miage.numres.cobuild.model.Utilisateur;

public interface UtilisateurService {
    public void uploadProfilePicture(String username, MultipartFile  picture);
    public Utilisateur getByUsername(String username);
}
