package unice.miage.numres.cobuild.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.requestModel.ProjetWithTacheRequestModel;
import unice.miage.numres.cobuild.services.ProjetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjetController {

    @Autowired
    private final ProjetService projetService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Projet> createProject(
            @RequestBody Projet projet,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.createProject(projet, userDetails.getUsername()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Projet>> getAllProjects() {
        return ResponseEntity.ok(projetService.getAllProjects());
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Projet>> getUserProjects(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.getUserProjects(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Optional<Projet>> getProjectById(@PathVariable String id) {
        return ResponseEntity.ok(projetService.getProjectById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Projet> updateProject(
            @PathVariable String id,
            @RequestBody Projet projet,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.updateProject(id, projet, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Void> deleteProject(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        projetService.deleteProject(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/with-tasks")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Projet> createProjectWithTasks(
            @RequestBody ProjetWithTacheRequestModel projet,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.createProjectWithTasks(projet.getProjet(), projet.getTaches(), userDetails.getUsername()));
    }

    @PostMapping("/{projectId}/tasks")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Tache> addTaskToProject(
            @PathVariable String projectId,
            @RequestBody Tache tache,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.addTaskToProject(projectId, tache, userDetails.getUsername()));
    }
    
    @PutMapping("/{projectId}/tasks/{taskId}/assign")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Tache> assignTask(
            @PathVariable String projectId,
            @PathVariable String taskId,
            @RequestParam String travailleurId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.assignTask(projectId, taskId, travailleurId, userDetails.getUsername()));
    }
}
