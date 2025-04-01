package unice.miage.numres.cobuild.servicesImpl;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Etape;
import unice.miage.numres.cobuild.model.Poste;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.repository.EtapeRepository;
import unice.miage.numres.cobuild.repository.PosteRepository;
import unice.miage.numres.cobuild.repository.ProjetRepository;
import unice.miage.numres.cobuild.repository.TacheRepository;
import unice.miage.numres.cobuild.repository.TravailleurRepository;
import unice.miage.numres.cobuild.requestModel.GeoCodeResult;
import unice.miage.numres.cobuild.services.TravailleurService;
import unice.miage.numres.cobuild.util.GeoUtils;
import unice.miage.numres.cobuild.util.StatutEtape;

import org.springframework.security.access.AccessDeniedException;


@Service
@RequiredArgsConstructor
public class TravailleurServiceImpl implements TravailleurService {

    private final TravailleurRepository travailleurRepository;
    private final ProjetRepository projetRepository;
    private final PosteRepository posteRepository;
    private final TacheRepository tacheRepository;
    private final EtapeRepository etapeRepository;

    private final GeoCodingService geoCodingService; // Assuming you have a service for geocoding

    @Override
    public Travailleur getProfile(String username) {
        return travailleurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Travailleur non trouvé."));
    }

    @Override
    public Travailleur updateProfile(String username, Travailleur updated) {
        Travailleur existing = travailleurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Travailleur non trouvé."));

        existing.setCompetences(updated.getCompetences());
        // You can update other fields too if allowed (nom, email, etc.)

        return travailleurRepository.save(existing);
    }

    @Override
    public List<Projet> getAvailableProjects() {
        return projetRepository.findByArchivedFalseAndStatutNot("TERMINE");
    }

    @Override
    public List<Poste> getOpenPostesInProject(String projectId) {
        return posteRepository.findByProjetIdAndTravailleurIsNull(projectId);
    }

    @Override
    public List<Poste> searchPostesByCompetence(String keyword) {
        return posteRepository.findByCompetencesRequisesContainingIgnoreCase(keyword);
    }

    @Override
    public List<Tache> getAssignedTasks(String username) {
        Travailleur travailleur = travailleurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Travailleur non trouvé."));

        return tacheRepository.findByTravailleurs(travailleur);
    }

    @Override
    public Etape updateEtapeStatut(String etapeId, StatutEtape nouveauStatut) {
        Etape etape = etapeRepository.findById(etapeId)
                .orElseThrow(() -> new RuntimeException("Étape non trouvée."));

        etape.setStatut(nouveauStatut);
        return etapeRepository.save(etape);
    }

    @Override
    public List<Poste> searchPostes(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return posteRepository.findAll(); // return all open postes if no keyword
        }
    
        return posteRepository.findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                keyword, keyword);
    }

    @Override
    public Projet getProjectById(String projectId) {
        return projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé."));
    }

    @Override
    public List<Projet> findProjectsNearAddress(String address, double radiusKm) {
        GeoCodeResult location = geoCodingService.geocodeAddress(address); // returns lat & lon

        if (location == null || location.getLatitude() == null || location.getLongitude() == null) {
            throw new RuntimeException("Adresse non trouvée: " + address);
        }

        double targetLat = location.getLatitude();
        double targetLon = location.getLongitude();

        List<Projet> allProjects = projetRepository.findAll();

        return allProjects.stream()
                .filter(projet -> projet.getLatitude() != null && projet.getLongitude() != null)
                .filter(projet -> {
                    double distance = GeoUtils.haversine(
                            targetLat,
                            targetLon,
                            projet.getLatitude(),
                            projet.getLongitude()
                    );
                    return distance <= radiusKm;
                })
                .toList();
    }

    @Override
public List<Projet> searchProjectsByKeyword(String keyword) {
    return projetRepository.searchByKeyword(keyword);
}
}
