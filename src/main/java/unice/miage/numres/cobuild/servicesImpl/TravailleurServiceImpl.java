package unice.miage.numres.cobuild.servicesImpl;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.repository.ProjetRepository;
import unice.miage.numres.cobuild.repository.TacheRepository;
import unice.miage.numres.cobuild.repository.TravailleurRepository;
import unice.miage.numres.cobuild.services.TravailleurService;
import org.springframework.security.access.AccessDeniedException;


@Service
@RequiredArgsConstructor
public class TravailleurServiceImpl implements TravailleurService {

    private final TravailleurRepository travailleurRepository;
    private final ProjetRepository projetRepository;
    private final TacheRepository tacheRepository;

    @Override
    public List<Projet> getAvailableProjects() {
        return projetRepository.findByStatutNot("DONE");
    }

    @Override
    public List<Projet> getVolunteeredProjects(String username) {
        Travailleur travailleur = travailleurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Travailleur not found"));
        return projetRepository.findByVolontairesContaining(travailleur);
    }

    @Override
    public List<Tache> getAssignedTasks(String username) {
        Travailleur travailleur = travailleurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Travailleur not found"));
        return tacheRepository.findTachesByTravailleur(travailleur);
    }

    @Override
    public void volunteerForProject(String username, String projetId) {
        Travailleur travailleur = travailleurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Travailleur not found"));

        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (projet.getVolontaires().contains(travailleur)) {
            throw new RuntimeException("Already volunteered for this project");
        }

        projet.getVolontaires().add(travailleur);
        projetRepository.save(projet);
    }

    @Override
    public Tache completeTask(String taskId, String username) {
        Tache tache = tacheRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!tache.getTravailleur().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only complete your assigned tasks.");
        }

        tache.setStatut("COMPLETED");
        return tacheRepository.save(tache);
    }

    @Override
    public void withdrawFromProject(String username, String projetId) {
        Travailleur travailleur = travailleurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Travailleur not found"));

        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!projet.getVolontaires().contains(travailleur)) {
            throw new RuntimeException("You are not enrolled in this project.");
        }

        projet.getVolontaires().remove(travailleur);
        projetRepository.save(projet);
    }
}
