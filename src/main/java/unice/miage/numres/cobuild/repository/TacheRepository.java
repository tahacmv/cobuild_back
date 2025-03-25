package unice.miage.numres.cobuild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;

@Repository
public interface TacheRepository extends JpaRepository<Tache, String> {
    
    // Retrieve all tasks in a specific project
    List<Tache> findByProjetId(String projetId);
    
    // Retrieve tasks that are not yet assigned to a travailleur
    List<Tache> findByTravailleursIsEmpty();

    List<Tache> findByTravailleurs(Travailleur travailleur);
}