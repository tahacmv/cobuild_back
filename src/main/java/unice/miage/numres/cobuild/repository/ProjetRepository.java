package unice.miage.numres.cobuild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Travailleur;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, String> {
    
    // Retrieve all projects created by a specific PorteurDeProjet (filtered by username)
    List<Projet> findByPorteurDeProjetUsername(String username);

    List<Projet> findByVolontairesContaining(Travailleur travailleur);

    List<Projet> findByStatutNot(String statut);

}
