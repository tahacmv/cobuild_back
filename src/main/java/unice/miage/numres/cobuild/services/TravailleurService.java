package unice.miage.numres.cobuild.services;

import java.util.List;

import unice.miage.numres.cobuild.model.Etape;
import unice.miage.numres.cobuild.model.Poste;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.util.StatutEtape;

public interface TravailleurService {
    // === Profil ===

    Travailleur getProfile(String username);
    Travailleur updateProfile(String username, Travailleur updated);

    // === Navigation et recherche ===

    List<Projet> getAvailableProjects();
    Projet getProjectById(String projectId);
    List<Poste> getOpenPostesInProject(String projectId);
    List<Poste> searchPostesByCompetence(String keyword);
    List<Poste> searchPostes(String keyword);
    List<Projet> findProjectsNearAddress(String address, double radiusKm);
    List<Projet> searchProjectsByKeyword(String keyword);

    // === Tâches et participation ===

    List<Tache> getAssignedTasks(String username);
    Etape updateEtapeStatut(String etapeId, StatutEtape nouveauStatut);
}