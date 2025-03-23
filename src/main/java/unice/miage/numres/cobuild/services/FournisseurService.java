package unice.miage.numres.cobuild.services;

import java.util.List;

import unice.miage.numres.cobuild.model.Materiel;
import unice.miage.numres.cobuild.model.Projet;

public interface FournisseurService {
    List<Projet> getAvailableProjects();
    void offerMaterialToProject(String username, String projetId, Materiel materiel);
    List<Materiel> getMyContributions(String username);
}
