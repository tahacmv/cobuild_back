package unice.miage.numres.cobuild.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Travailleur;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, String> {
    
    // Retrieve all projects created by a specific PorteurDeProjet (filtered by username)
    List<Projet> findByPorteurDeProjetUsername(String username);

    List<Projet> findByPorteurDeProjetUsernameAndStatutContainingIgnoreCaseAndNomContainingIgnoreCase(
        String username, String statut, String nom);

    List<Projet> findByStatutNot(String statut);

    List<Projet> findByArchivedFalseAndStatutNot(String string);

    @Query("SELECT p FROM Projet p " +
       "WHERE LOWER(p.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
       "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Projet> searchByKeyword(@Param("keyword") String keyword);


}
