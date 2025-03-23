package unice.miage.numres.cobuild.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Materiel;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.services.FournisseurService;

@RestController
@RequestMapping("/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {
    @Autowired
    private final FournisseurService fournisseurService;

        @GetMapping("/projects")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<List<Projet>> getAvailableProjects() {
        return ResponseEntity.ok(fournisseurService.getAvailableProjects());
    }

    @PostMapping("/projects/{projetId}/offer")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<String> offerMaterial(
            @PathVariable String projetId,
            @RequestBody Materiel materiel,
            @AuthenticationPrincipal UserDetails userDetails) {
        fournisseurService.offerMaterialToProject(userDetails.getUsername(), projetId, materiel);
        return ResponseEntity.ok("Material offered successfully.");
    }

    @GetMapping("/contributions")
    @PreAuthorize("hasRole('FOURNISSEUR')")
    public ResponseEntity<List<Materiel>> getMyContributions(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(fournisseurService.getMyContributions(userDetails.getUsername()));
    }
}
