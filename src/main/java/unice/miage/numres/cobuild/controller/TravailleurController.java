package unice.miage.numres.cobuild.controller;

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

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Etape;
import unice.miage.numres.cobuild.model.Poste;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.services.TravailleurService;
import unice.miage.numres.cobuild.util.StatutEtape;

@RestController
@RequestMapping("/travailleurs")
@RequiredArgsConstructor
public class TravailleurController {

    @Autowired
    private final TravailleurService travailleurService;
    // === Profil ===

    @GetMapping("/me")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<Travailleur> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(travailleurService.getProfile(userDetails.getUsername()));
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<Travailleur> updateProfile(
            @RequestBody Travailleur updated,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(travailleurService.updateProfile(userDetails.getUsername(), updated));
    }

    // === Navigation & Recherche ===

    @GetMapping("/projects")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Projet>> getAvailableProjects() {
        return ResponseEntity.ok(travailleurService.getAvailableProjects());
    }

    @GetMapping("/projects/{projectId}")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<Projet> getProjectById(@PathVariable String projectId) {
        return ResponseEntity.ok(travailleurService.getProjectById(projectId));
    }

    @GetMapping("/projects/{projectId}/postes")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Poste>> getOpenPostesInProject(@PathVariable String projectId) {
        return ResponseEntity.ok(travailleurService.getOpenPostesInProject(projectId));
    }

    @GetMapping("/postes/search")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Poste>> searchPostesByCompetence(@RequestParam String keyword) {
        return ResponseEntity.ok(travailleurService.searchPostes(keyword));
    }

    // === Tâches & Étapes ===

    @GetMapping("/tasks")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Tache>> getAssignedTasks(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(travailleurService.getAssignedTasks(userDetails.getUsername()));
    }

    @PutMapping("/etapes/{etapeId}/status")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<Etape> updateEtapeStatut(
            @PathVariable String etapeId,
            @RequestParam StatutEtape statut) {
        return ResponseEntity.ok(travailleurService.updateEtapeStatut(etapeId, statut));
    }

    @GetMapping("/projects/nearby")
    public ResponseEntity<List<Projet>> getNearbyProjects(
            @RequestParam String address,
            @RequestParam(defaultValue = "10") double radiusKm) {
        return ResponseEntity.ok(travailleurService.findProjectsNearAddress(address, radiusKm));
    }

    @GetMapping("/projects/search")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Projet>> searchProjectsByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(travailleurService.searchProjectsByKeyword(keyword));
    }
    
}
