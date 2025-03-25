package unice.miage.numres.cobuild.services;

import java.nio.file.AccessDeniedException;
import java.util.List;

import unice.miage.numres.cobuild.model.Candidature;
import unice.miage.numres.cobuild.util.StatutCandidature;

public interface CandidatureService {
    // === Candidature par un travailleur ===

    void postulerAUnPoste(String username, String posteId);
    List<Candidature> getMesCandidatures(String username);

    // === Traitement par le porteur de projet ===

    List<Candidature> getCandidaturesDuPoste(String username, String posteId);
    void accepterCandidature(String candidatureId, String username);
    void refuserCandidature(String candidatureId, String username);


    // === Statut et analyse ===

    List<Candidature> getCandidaturesParStatut(String posteId, StatutCandidature statut);
    boolean hasAlreadyApplied(String username, String posteId);
    long countCandidatures(String posteId);
    long countAcceptedCandidatures(String posteId);
}
 