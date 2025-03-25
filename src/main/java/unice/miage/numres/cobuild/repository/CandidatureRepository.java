package unice.miage.numres.cobuild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.Candidature;
import unice.miage.numres.cobuild.util.StatutCandidature;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, String> {
    List<Candidature> findByPoste_Projet_Id(String projetId);
    List<Candidature> findByTravailleurUsername(String username);
    Optional<Candidature> findByPosteIdAndTravailleurId(String projetId, String travailleurId);
    List<Candidature> findByPosteId(String jobPostId);
    boolean existsByTravailleurIdAndPosteId(String id, String id2);
    List<Candidature> findByPosteIdAndStatut(String posteId, StatutCandidature statut);
    long countByPosteId(String posteId);
    long countByPosteIdAndStatut(String posteId, StatutCandidature acceptee);
}
