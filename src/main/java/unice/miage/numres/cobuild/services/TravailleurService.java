package unice.miage.numres.cobuild.services;

import java.util.List;

import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;

public interface TravailleurService {
    void volunteerForProject(String username, String projetId);
    List<Projet> getAvailableProjects();
    List<Projet> getVolunteeredProjects(String username);
    List<Tache> getAssignedTasks(String username);
    Tache completeTask(String taskId, String username);
    void withdrawFromProject(String username, String projetId);
}