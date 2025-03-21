package unice.miage.numres.cobuild.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.services.TravailleurService;

@RestController
@RequestMapping("/travailleurs")
@RequiredArgsConstructor
public class TravailleurController {

    @Autowired
    private final TravailleurService travailleurService;

    @GetMapping("/projects/available")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Projet>> getAvailableProjects() {
        return ResponseEntity.ok(travailleurService.getAvailableProjects());
    }

    @GetMapping("/projects/mine")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Projet>> getMyVolunteeredProjects(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(travailleurService.getVolunteeredProjects(userDetails.getUsername()));
    }

    @GetMapping("/tasks/assigned")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<List<Tache>> getAssignedTasks(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(travailleurService.getAssignedTasks(userDetails.getUsername()));
    }

    @PostMapping("/volunteer/{projectId}")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<String> volunteerForProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserDetails userDetails) {

        travailleurService.volunteerForProject(userDetails.getUsername(), projectId);
        return ResponseEntity.ok("You have successfully volunteered for the project.");
    }

    @PutMapping("/tasks/{taskId}/complete")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<Tache> completeTask(
            @PathVariable String taskId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(travailleurService.completeTask(taskId, userDetails.getUsername()));
    }

    @DeleteMapping("/projects/{projectId}/withdraw")
    @PreAuthorize("hasRole('TRAVAILLEUR')")
    public ResponseEntity<String> withdrawFromProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserDetails userDetails) {
        travailleurService.withdrawFromProject(userDetails.getUsername(), projectId);
        return ResponseEntity.ok("Successfully withdrawn from the project.");
    }
}
