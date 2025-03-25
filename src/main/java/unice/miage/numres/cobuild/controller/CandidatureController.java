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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Candidature;
import unice.miage.numres.cobuild.services.CandidatureService;
import unice.miage.numres.cobuild.util.StatutCandidature;

@RestController
@RequestMapping("/candidatures")
@RequiredArgsConstructor
public class CandidatureController {

    private final CandidatureService candidatureService;


    // === Travailleur: Postuler et voir ses candidatures ===

    @PostMapping("/apply/{posteId}")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<String> postulerAUnPoste(
            @PathVariable String posteId,
            @AuthenticationPrincipal UserDetails userDetails) {
        candidatureService.postulerAUnPoste(userDetails.getUsername(), posteId);
        return ResponseEntity.ok("Candidature envoyée avec succès !");
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Candidature>> getMesCandidatures(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(candidatureService.getMesCandidatures(userDetails.getUsername()));
    }


    // === Porteur de projet: gestion des candidatures ===

    @GetMapping("/poste/{posteId}")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Candidature>> getCandidaturesDuPoste(
            @PathVariable String posteId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(candidatureService.getCandidaturesDuPoste(userDetails.getUsername(), posteId));
    }

    @PutMapping("/{candidatureId}/accept")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<String> accepterCandidature(
            @PathVariable String candidatureId,
            @AuthenticationPrincipal UserDetails userDetails) {
        candidatureService.accepterCandidature(candidatureId, userDetails.getUsername());
        return ResponseEntity.ok("Candidature acceptée.");
    }

    @PutMapping("/{candidatureId}/reject")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<String> refuserCandidature(
            @PathVariable String candidatureId,
            @AuthenticationPrincipal UserDetails userDetails) {
        candidatureService.refuserCandidature(candidatureId, userDetails.getUsername());
        return ResponseEntity.ok("Candidature refusée.");
    }


    // === Analyse et statut ===

    @GetMapping("/poste/{posteId}/statut/{statut}")
    public ResponseEntity<List<Candidature>> getCandidaturesParStatut(
            @PathVariable String posteId,
            @PathVariable StatutCandidature statut) {
        return ResponseEntity.ok(candidatureService.getCandidaturesParStatut(posteId, statut));
    }

    @GetMapping("/poste/{posteId}/check")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<Boolean> hasAlreadyApplied(
            @PathVariable String posteId,
            @AuthenticationPrincipal UserDetails userDetails) {
        boolean alreadyApplied = candidatureService.hasAlreadyApplied(userDetails.getUsername(), posteId);
        return ResponseEntity.ok(alreadyApplied);
    }

    @GetMapping("/poste/{posteId}/count")
    public ResponseEntity<Long> countCandidatures(@PathVariable String posteId) {
        return ResponseEntity.ok(candidatureService.countCandidatures(posteId));
    }

    @GetMapping("/poste/{posteId}/count/accepted")
    public ResponseEntity<Long> countAcceptedCandidatures(@PathVariable String posteId) {
        return ResponseEntity.ok(candidatureService.countAcceptedCandidatures(posteId));
    }
}
