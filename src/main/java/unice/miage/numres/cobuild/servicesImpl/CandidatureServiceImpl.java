package unice.miage.numres.cobuild.servicesImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Candidature;
import unice.miage.numres.cobuild.model.Poste;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.repository.CandidatureRepository;
import unice.miage.numres.cobuild.repository.PosteRepository;
import unice.miage.numres.cobuild.repository.ProjetRepository;
import unice.miage.numres.cobuild.repository.TravailleurRepository;
import unice.miage.numres.cobuild.services.CandidatureService;
import unice.miage.numres.cobuild.util.StatutCandidature;
import org.springframework.security.access.AccessDeniedException;


@Service
@RequiredArgsConstructor
public class CandidatureServiceImpl implements CandidatureService {

    private final CandidatureRepository candidatureRepository;
    private final PosteRepository posteRepository;
    private final TravailleurRepository travailleurRepository;
    private final ProjetRepository projetRepository;

    @Override
    public void postulerAUnPoste(String username, String posteId) {
        Poste poste = posteRepository.findById(posteId)
            .orElseThrow(() -> new RuntimeException("Poste non trouvé"));

        Travailleur travailleur = travailleurRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Travailleur non trouvé"));

        boolean dejaPostule = candidatureRepository.existsByTravailleurIdAndPosteId(travailleur.getId(), poste.getId());
        if (dejaPostule) {
            throw new RuntimeException("Vous avez déjà postulé à ce poste.");
        }

        Candidature candidature = new Candidature();
        candidature.setPoste(poste);
        candidature.setTravailleur(travailleur);
        candidature.setStatut(StatutCandidature.EN_ATTENTE);
        candidature.setDateCandidature(LocalDateTime.now());

        candidatureRepository.save(candidature);
    }

    @Override
    public List<Candidature> getMesCandidatures(String username) {
        return candidatureRepository.findByTravailleurUsername(username);
    }

    @Override
    public List<Candidature> getCandidaturesDuPoste(String username, String posteId) {
        Poste poste = posteRepository.findById(posteId)
                .orElseThrow(() -> new RuntimeException("Poste non trouvé."));
    
        Projet projet = poste.getProjet();
        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Accès refusé.");
        }
    
        return candidatureRepository.findByPosteId(posteId);
    }

    @Override
    public void accepterCandidature(String candidatureId, String username) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée."));
    
        Poste poste = candidature.getPoste();
        Projet projet = poste.getProjet();
    
        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez accepter que les candidatures à vos propres projets.");
        }
    
        // Mark this one as accepted
        candidature.setStatut(StatutCandidature.ACCEPTEE);
        candidatureRepository.save(candidature);
    
        // Reject the others
        List<Candidature> autres = candidatureRepository.findByPosteId(poste.getId());
        for (Candidature autre : autres) {
            if (!autre.getId().equals(candidature.getId())) {
                autre.setStatut(StatutCandidature.REJETEE);
                candidatureRepository.save(autre);
            }
        }
    
        // Affect the worker to the poste
        poste.setTravailleur(candidature.getTravailleur());
        posteRepository.save(poste);
    }

    @Override
    public void refuserCandidature(String candidatureId, String username) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature non trouvée."));

        Poste poste = candidature.getPoste();
        Projet projet = poste.getProjet();

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez refuser que les candidatures à vos propres projets.");
        }

        candidature.setStatut(StatutCandidature.REJETEE);
        candidatureRepository.save(candidature);
    }

    @Override
    public List<Candidature> getCandidaturesParStatut(String posteId, StatutCandidature statut) {
        return candidatureRepository.findByPosteIdAndStatut(posteId, statut);
    }

    @Override
    public boolean hasAlreadyApplied(String username, String posteId) {
        Travailleur travailleur = travailleurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Travailleur non trouvé"));
        return candidatureRepository.existsByTravailleurIdAndPosteId(travailleur.getId(), posteId);
    }

    @Override
    public long countCandidatures(String posteId) {
        return candidatureRepository.countByPosteId(posteId);
    }

    @Override
    public long countAcceptedCandidatures(String posteId) {
        return candidatureRepository.countByPosteIdAndStatut(posteId, StatutCandidature.ACCEPTEE);
    }

}
