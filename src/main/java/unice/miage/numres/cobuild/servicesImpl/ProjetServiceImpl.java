package unice.miage.numres.cobuild.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import unice.miage.numres.cobuild.model.Etape;
import unice.miage.numres.cobuild.model.PorteurDeProjet;
import unice.miage.numres.cobuild.model.Poste;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.repository.EtapeRepository;
import unice.miage.numres.cobuild.repository.PorteurDeProjetRepository;
import unice.miage.numres.cobuild.repository.PosteRepository;
import unice.miage.numres.cobuild.repository.ProjetRepository;
import unice.miage.numres.cobuild.repository.TacheRepository;
import unice.miage.numres.cobuild.repository.TravailleurRepository;
import unice.miage.numres.cobuild.services.ProjetService;
import unice.miage.numres.cobuild.util.StatutEtape;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository projetRepository;
    private final PorteurDeProjetRepository porteurDeProjetRepository;
    private final TacheRepository tacheRepository;
    private final TravailleurRepository travailleurRepository;
    private final PosteRepository posteRepository;
    private final EtapeRepository etapeRepository;

    @Override
    public Projet createProject(Projet projet, String username) {
        PorteurDeProjet porteur = porteurDeProjetRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Porteur de projet non trouvé"));

        projet.setPorteurDeProjet(porteur);
        return projetRepository.save(projet);
    }

    @Override
    public Projet createProjectWithTasks(Projet projet, List<Tache> taches, String username) {
        PorteurDeProjet porteur = porteurDeProjetRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Porteur de projet non trouvé"));

        projet.setPorteurDeProjet(porteur);

        // Set the project for each task before saving
        for (Tache tache : taches) {
            tache.setProjet(projet);
        }

        projet.setTaches(taches);
        return projetRepository.save(projet);
    }

    @Override
    public List<Projet> getAllProjects() {
        return projetRepository.findAll();
    }

    @Override
    public List<Projet> getUserProjects(String username) {
        return projetRepository.findByPorteurDeProjetUsername(username);
    }

    @Override
    public List<Projet> getUserProjectsFiltered(String username, String status, String keyword) {
        return projetRepository.findByPorteurDeProjetUsernameAndStatutContainingIgnoreCaseAndNomContainingIgnoreCase(
                username,
                status != null ? status : "",
                keyword != null ? keyword : ""
        );
    }

    @Override
    public Optional<Projet> getProjectById(String id) {
        return projetRepository.findById(id);
    }

    @Override
    public Projet updateProject(String id, Projet projetDetails, String username) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez modifier que vos propres projets.");
        }

        projet.setNom(projetDetails.getNom());
        projet.setDescription(projetDetails.getDescription());
        projet.setStatut(projetDetails.getStatut());

        return projetRepository.save(projet);
    }

    @Override
    public void deleteProject(String id, String username) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez supprimer que vos propres projets.");
        }

        projetRepository.delete(projet);
    }

    @Override
    public Tache addTaskToProject(String projectId, Tache tache, String username) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez modifier que vos propres projets.");
        }

        tache.setProjet(projet);
        return tacheRepository.save(tache);
    }

    @Override
    public Tache assignTaskToTravailleurs(String projectId, String taskId, List<String> travailleurIds, String username) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez gérer que vos propres projets.");
        }

        Tache tache = tacheRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

        // Optional: check if task belongs to this project
        if (!tache.getProjet().getId().equals(projectId)) {
            throw new RuntimeException("Cette tâche n'appartient pas au projet.");
        }

        List<Travailleur> travailleurs = travailleurRepository.findAllById(travailleurIds);
        tache.setTravailleurs(travailleurs);

        return tacheRepository.save(tache);
    }

    @Override
    public List<Tache> getTasksByProject(String projectId, String username) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez consulter que vos propres projets.");
        }

        return tacheRepository.findByProjetId(projectId);
    }

    @Override
    public Map<String, Long> getTaskStepStatusCount(String taskId) {
        Tache tache = tacheRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée."));

        return tache.getEtapes().stream()
                .collect(Collectors.groupingBy(
                    etape -> etape.getStatut().name(),
                    Collectors.counting()
                ));
    }

    @Override
public Etape addStepToTask(String taskId, Etape etape, String username) {
    Tache tache = tacheRepository.findById(taskId)
        .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

    Projet projet = tache.getProjet();

    if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
        throw new AccessDeniedException("Vous ne pouvez modifier que vos propres tâches.");
    }

    etape.setTache(tache);
    return etapeRepository.save(etape);
}

@Override
public List<Etape> getStepsByTask(String taskId, String username) {
    Tache tache = tacheRepository.findById(taskId)
        .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

    Projet projet = tache.getProjet();

    if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
        throw new AccessDeniedException("Accès refusé.");
    }

    return etapeRepository.findByTacheId(taskId);
}
@Override
public void removeStepFromTask(String stepId, String username) {
    Etape etape = etapeRepository.findById(stepId)
        .orElseThrow(() -> new RuntimeException("Étape non trouvée"));

    Tache tache = etape.getTache();
    Projet projet = tache.getProjet();

    if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
        throw new AccessDeniedException("Vous ne pouvez modifier que vos propres projets.");
    }

    etapeRepository.delete(etape);
}
    @Override
    public Poste addPosteToProject(String projectId, Poste poste, String username) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez modifier que vos propres projets.");
        }

        poste.setProjet(projet);
        return posteRepository.save(poste);
    }

    @Override
    public List<Poste> getPostesByProject(String projectId, String username) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Accès refusé.");
        }

        return posteRepository.findByProjetId(projectId);
    }

    @Override
    public double getTaskCompletionPercentage(String taskId) {
        Tache tache = tacheRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée."));

        List<Etape> etapes = tache.getEtapes();
        if (etapes == null || etapes.isEmpty()) {
            return 0.0;
        }

        long total = etapes.size();
        long completed = etapes.stream()
                .filter(e -> e.getStatut() == StatutEtape.TERMINEE)
                .count();

        return (completed * 100.0) / total;
    }


    @Override
    public Projet archiveProject(String projectId, String username) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));
    
        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous ne pouvez archiver que vos propres projets.");
        }
    
        projet.setArchived(true);
        return projetRepository.save(projet);
    }
    

    @Override
    public List<Travailleur> getTravailleursInProject(String projectId, String username) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));
    
        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("Accès refusé.");
        }
    
        List<Poste> postes = posteRepository.findByProjetId(projectId);
    
        return postes.stream()
                .map(Poste::getTravailleur)
                .filter(Objects::nonNull) // Only include filled posts
                .collect(Collectors.toList());
    }

}
