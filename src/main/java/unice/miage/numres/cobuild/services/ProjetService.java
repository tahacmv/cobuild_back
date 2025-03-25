package unice.miage.numres.cobuild.services;

import unice.miage.numres.cobuild.model.Etape;
import unice.miage.numres.cobuild.model.Poste;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjetService {
    // === Project Management ===
    Projet createProject(Projet projet, String username);
    Projet createProjectWithTasks(Projet projet, List<Tache> taches, String username);
    List<Projet> getAllProjects();
    List<Projet> getUserProjects(String username);
    List<Projet> getUserProjectsFiltered(String username, String status, String keyword);
    Optional<Projet> getProjectById(String id);
    Projet updateProject(String id, Projet projetDetails, String username);
    void deleteProject(String id, String username);

        // === Task Management ===
    Tache addTaskToProject(String projectId, Tache tache, String username);
    Tache assignTaskToTravailleurs(String projectId, String taskId, List<String> travailleurIds, String username);
    List<Tache> getTasksByProject(String projectId, String username);
    double getTaskCompletionPercentage(String taskId);
    Map<String, Long> getTaskStepStatusCount(String taskId); // e.g. {COMMENCEE=1, EN_COURS=2, TERMINEE=3}
    Etape addStepToTask(String taskId, Etape etape, String username);
    List<Etape> getStepsByTask(String taskId, String username);
    void removeStepFromTask(String stepId, String username);

    // === Poste Management ===
    Poste addPosteToProject(String projectId, Poste poste, String username);
    List<Poste> getPostesByProject(String projectId, String username);
    Projet archiveProject(String projectId, String username);


    // === Collaborator Overview ===
    List<Travailleur> getTravailleursInProject(String projectId, String username);

}
