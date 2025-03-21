package unice.miage.numres.cobuild.services;

import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;

import java.util.List;
import java.util.Optional;

public interface ProjetService {
    Projet createProject(Projet projet, String username);
    Projet createProjectWithTasks(Projet projet, List<Tache> taches, String username);
    List<Projet> getAllProjects();
    List<Projet> getUserProjects(String username);
    Optional<Projet> getProjectById(String id);
    Projet updateProject(String id, Projet projetDetails, String username);
    void deleteProject(String id, String username);
    Tache addTaskToProject(String projectId, Tache tache, String username);
    Tache assignTask(String projectId, String taskId, String travailleurId, String username);

}
