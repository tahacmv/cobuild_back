package unice.miage.numres.cobuild.servicesImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Fournisseur;
import unice.miage.numres.cobuild.model.Materiel;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.repository.FournisseurRepository;
import unice.miage.numres.cobuild.repository.MaterielRepository;
import unice.miage.numres.cobuild.repository.ProjetRepository;
import unice.miage.numres.cobuild.services.FournisseurService;

@Service
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService{
    private final FournisseurRepository fournisseurRepository;
    private final ProjetRepository projetRepository;
    private final MaterielRepository materielRepository;

    @Override
    public List<Projet> getAvailableProjects() {
        return projetRepository.findByStatutNot("DONE");
    }

    @Override
    public void offerMaterialToProject(String username, String projetId, Materiel materiel) {
        Fournisseur fournisseur = fournisseurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));

        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet not found"));

        materiel.setFournisseur(fournisseur);
        materiel.setProjet(projet);

        materielRepository.save(materiel);
    }

    @Override
    public List<Materiel> getMyContributions(String username) {
        Fournisseur fournisseur = fournisseurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));

        return materielRepository.findByFournisseur(fournisseur);
    }
}
