package unice.miage.numres.cobuild.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Utilisateur;
import unice.miage.numres.cobuild.services.UtilisateurService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UtilisateurController {
    private final UtilisateurService userDetailService;

    @PutMapping("/me/upload-profile-picture")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> uploadProfilePicture(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam("file") MultipartFile file) throws IOException {
    userDetailService.uploadProfilePicture(userDetails.getUsername(), file);
    return ResponseEntity.ok("Profile picture updated");
}

    @GetMapping("/me")
    public ResponseEntity<Utilisateur> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Utilisateur utilisateur = userDetailService.getByUsername(userDetails.getUsername());
        return ResponseEntity.ok(utilisateur);
    }
}
