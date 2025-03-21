package unice.miage.numres.cobuild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import unice.miage.numres.cobuild.model.Fournisseur;
import unice.miage.numres.cobuild.model.PorteurDeProjet;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.model.Utilisateur;

@Service
public interface AdminService {
    // User services
    List<Utilisateur> getAllUsers();
    Optional<Utilisateur> getUserById(String id);
    Utilisateur updateUser(String id, Utilisateur userDetails);
    void deleteUser(String id);
    void assignRole(String userId, String roleName);

    // Porteur de projet services
    List<PorteurDeProjet> getAllPorteurs();
    Optional<PorteurDeProjet> getPorteurById(String id);
    PorteurDeProjet updatePorteur(String id, PorteurDeProjet userDetails);
    void deletePorteur(String id);

    // Travailleur services
    List<Travailleur> getAllTravailleurs();
    Optional<Travailleur> getTravailleurById(String id);
    Travailleur updateTravailleur(String id, Travailleur userDetails);
    void deleteTravailleur(String id);

    // Fournisseur services
    List<Fournisseur> getAllFournisseurs();
    Optional<Fournisseur> getFournisseurById(String id);
    Fournisseur updateFournisseur(String id, Fournisseur userDetails);
    void deleteFournisseur(String id);
}
