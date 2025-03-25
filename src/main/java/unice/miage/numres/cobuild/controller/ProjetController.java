package unice.miage.numres.cobuild.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import unice.miage.numres.cobuild.model.Etape;
import unice.miage.numres.cobuild.model.Poste;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.services.ProjetService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjetController {

    @Autowired
    private final ProjetService projetService;

    // === Project Management ===

    @PostMapping
    @PreAuthorize("hasRole('PORTEURDEPROJET') or hasRole('ADMIN')")
    public ResponseEntity<Projet> createProject(
            @RequestBody Projet projet,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.createProject(projet, userDetails.getUsername()));
    }

    @PostMapping("/with-tasks")
    @PreAuthorize("hasAuthority('PORTEURDEPROJET')")
    public ResponseEntity<Projet> createProjectWithTasks(
            @RequestBody Projet projet,
            @RequestParam List<Tache> taches,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.createProjectWithTasks(projet, taches, userDetails.getUsername()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PORTEURDEPROJET')")
    public ResponseEntity<List<Projet>> getAllProjects() {
        return ResponseEntity.ok(projetService.getAllProjects());
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Projet>> getUserProjects(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.getUserProjects(userDetails.getUsername()));
    }

    @GetMapping("/mine/search")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Projet>> getUserProjectsFiltered(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.getUserProjectsFiltered(userDetails.getUsername(), status, keyword));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PORTEURDEPROJET', 'ADMIN')")
    public ResponseEntity<Projet> getProjectById(@PathVariable String id) {
        return projetService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Projet> updateProject(
            @PathVariable String id,
            @RequestBody Projet projetDetails,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.updateProject(id, projetDetails, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Void> deleteProject(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {
        projetService.deleteProject(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    // === Task Management ===

    @PostMapping("/{projectId}/tasks")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Tache> addTaskToProject(
            @PathVariable String projectId,
            @RequestBody Tache tache,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.addTaskToProject(projectId, tache, userDetails.getUsername()));
    }

    @PutMapping("/{projectId}/tasks/{taskId}/assign")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Tache> assignTaskToTravailleurs(
            @PathVariable String projectId,
            @PathVariable String taskId,
            @RequestBody List<String> travailleurIds,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.assignTaskToTravailleurs(projectId, taskId, travailleurIds, userDetails.getUsername()));
    }

    @GetMapping("/{projectId}/tasks")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Tache>> getTasksByProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.getTasksByProject(projectId, userDetails.getUsername()));
    }

    @PostMapping("/tasks/{taskId}/steps")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Etape> addStepToTask(
            @PathVariable String taskId,
            @RequestBody Etape etape,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.addStepToTask(taskId, etape, userDetails.getUsername()));
    }

    @GetMapping("/tasks/{taskId}/steps")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Etape>> getStepsByTask(
            @PathVariable String taskId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.getStepsByTask(taskId, userDetails.getUsername()));
    }

    @DeleteMapping("/tasks/steps/{stepId}")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Void> removeStepFromTask(
            @PathVariable String stepId,
            @AuthenticationPrincipal UserDetails userDetails) {
        projetService.removeStepFromTask(stepId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tasks/{taskId}/completion")
    public ResponseEntity<Double> getTaskCompletionPercentage(@PathVariable String taskId) {
        return ResponseEntity.ok(projetService.getTaskCompletionPercentage(taskId));
    }

    @GetMapping("/tasks/{taskId}/status-count")
    public ResponseEntity<Map<String, Long>> getTaskStepStatusCount(@PathVariable String taskId) {
        return ResponseEntity.ok(projetService.getTaskStepStatusCount(taskId));
    }

    // === Poste Management ===

    @PostMapping("/{projectId}/postes")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Poste> addPosteToProject(
            @PathVariable String projectId,
            @RequestBody Poste poste,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.addPosteToProject(projectId, poste, userDetails.getUsername()));
    }

    @GetMapping("/{projectId}/postes")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Poste>> getPostesByProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.getPostesByProject(projectId, userDetails.getUsername()));
    }

    // === Archive Project ===

    @PutMapping("/{projectId}/archive")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<Projet> archiveProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.archiveProject(projectId, userDetails.getUsername()));
    }

    // === Collaborator Overview ===

    @GetMapping("/{projectId}/travailleurs")
    @PreAuthorize("hasRole('PORTEURDEPROJET')")
    public ResponseEntity<List<Travailleur>> getTravailleursInProject(
            @PathVariable String projectId,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(projetService.getTravailleursInProject(projectId, userDetails.getUsername()));
    }
}
