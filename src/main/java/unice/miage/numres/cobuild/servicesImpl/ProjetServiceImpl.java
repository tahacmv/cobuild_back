package unice.miage.numres.cobuild.servicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import unice.miage.numres.cobuild.model.PorteurDeProjet;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.repository.PorteurDeProjetRepository;
import unice.miage.numres.cobuild.repository.ProjetRepository;
import unice.miage.numres.cobuild.repository.TacheRepository;
import unice.miage.numres.cobuild.repository.TravailleurRepository;
import unice.miage.numres.cobuild.services.ProjetService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjetServiceImpl implements ProjetService {

    private final ProjetRepository projetRepository;
    private final PorteurDeProjetRepository porteurDeProjetRepository;
    private final TacheRepository tacheRepository;
    private final TravailleurRepository travailleurRepository;


    @Override
    public Projet createProject(Projet projet, String username) {
        // Get the PorteurDeProjet user
        PorteurDeProjet porteur = porteurDeProjetRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("PorteurDeProjet not found"));

        projet.setPorteurDeProjet(porteur);
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
    public Optional<Projet> getProjectById(String id) {
        return projetRepository.findById(id);
    }

    @Override
    public Projet updateProject(String id, Projet projetDetails, String username) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only update your own projects");
        }

        projet.setNom(projetDetails.getNom());
        projet.setDescription(projetDetails.getDescription());
        projet.setStatut(projetDetails.getStatut());

        return projetRepository.save(projet);
    }

    @Override
    public void deleteProject(String id, String username) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only delete your own projects");
        }

        projetRepository.delete(projet);
    }
    @Override
    public Projet createProjectWithTasks(Projet projet, List<Tache> taches, String username) {
        // Retrieve the PorteurDeProjet
        PorteurDeProjet porteur = porteurDeProjetRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("PorteurDeProjet not found"));
    
        // Assign the project owner
        projet.setPorteurDeProjet(porteur);
        
        // Save the project first to get the ID
        Projet savedProject = projetRepository.save(projet);
    
        // Assign the saved project to each task and save them
        for (Tache tache : taches) {
            tache.setProjet(savedProject);  // Link task to project
            tacheRepository.save(tache);
        }
    
        // Fetch the project again with its tasks to return in the response
        return projetRepository.findById(savedProject.getId())
                .orElseThrow(() -> new RuntimeException("Error retrieving project"));
    }

    @Override
    public Tache addTaskToProject(String projectId, Tache tache, String username) {
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only add tasks to your own projects");
        }

        tache.setProjet(projet);
        return tacheRepository.save(tache);
    }

    @Override
    public Tache assignTask(String projectId, String taskId, String travailleurId, String username) {
        // Retrieve the project
        Projet projet = projetRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    
        // Ensure the authenticated user is the owner of the project
        if (!projet.getPorteurDeProjet().getUsername().equals(username)) {
            throw new AccessDeniedException("You can only assign tasks in your own projects");
        }
    
        // Retrieve the task and check that it belongs to the project
        Tache tache = tacheRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    
        if (!tache.getProjet().getId().equals(projet.getId())) {
            throw new RuntimeException("Task does not belong to the specified project");
        }
    
        // Retrieve the Travailleur
        Travailleur travailleur = travailleurRepository.findById(travailleurId)
                .orElseThrow(() -> new RuntimeException("Travailleur not found"));
    
        // Ensure the Travailleur is a volunteer in the project
        if (!projet.getVolontaires().contains(travailleur)) {
            throw new RuntimeException("Travailleur must be a volunteer in the project to be assigned a task.");
        }
    
        // Assign the Travailleur to the task
        tache.setTravailleur(travailleur);
        return tacheRepository.save(tache);
    }
    
}
