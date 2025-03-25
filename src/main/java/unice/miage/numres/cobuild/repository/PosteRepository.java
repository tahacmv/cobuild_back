package unice.miage.numres.cobuild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.Poste;

@Repository
public interface PosteRepository extends JpaRepository<Poste, String> {
    List<Poste> findByProjetId(String projetId);
    Poste findByTravailleurUsername(String username);
    Optional<Poste> findByIdAndTravailleur_Id(String id, String travailleurId);
    Optional<Poste> findById(String id);
    List<Poste> findByProjetIdAndTravailleurIsNull(String projectId);
    List<Poste> findByCompetencesRequisesContainingIgnoreCase(String keyword);
    
}
