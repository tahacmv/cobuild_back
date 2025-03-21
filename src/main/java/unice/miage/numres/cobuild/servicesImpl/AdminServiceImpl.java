package unice.miage.numres.cobuild.servicesImpl;

import java.util.List;
import java.util.Optional;

import javax.sound.sampled.Port;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Fournisseur;
import unice.miage.numres.cobuild.model.PorteurDeProjet;
import unice.miage.numres.cobuild.model.Role;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.model.Utilisateur;
import unice.miage.numres.cobuild.repository.FournisseurRepository;
import unice.miage.numres.cobuild.repository.PorteurDeProjetRepository;
import unice.miage.numres.cobuild.repository.RoleRepository;
import unice.miage.numres.cobuild.repository.TravailleurRepository;
import unice.miage.numres.cobuild.repository.UserRepository;
import unice.miage.numres.cobuild.services.AdminService;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PorteurDeProjetRepository porteurRepository;
    private final TravailleurRepository travailleurRepository;
    private final FournisseurRepository fournisseurRepository;

    @Override
    public List<Utilisateur> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Utilisateur> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Utilisateur updateUser(String id, Utilisateur userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setRoles(userDetails.getRoles());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void assignRole(String userId, String roleName) {
        Utilisateur user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(roleName.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    // Porteur de projet services
    @Override
    public List<PorteurDeProjet> getAllPorteurs() {
        return porteurRepository.findAll();
    }

    @Override
    public Optional<PorteurDeProjet> getPorteurById(String id) {
        return porteurRepository.findById(id);
    }

    @Override
    public PorteurDeProjet updatePorteur(String id, PorteurDeProjet userDetails) {
        return porteurRepository.findById(id)
                .map(porteur -> {
                    porteur.setUsername(userDetails.getUsername());
                    porteur.setEmail(userDetails.getEmail());
                    return porteurRepository.save(porteur);
                })
                .orElseThrow(() -> new RuntimeException("Porteur not found with id: " + id));
    }

    @Override
    public void deletePorteur(String id) {
        porteurRepository.deleteById(id);
    }

    // Travailleur services
    @Override
    public List<Travailleur> getAllTravailleurs() {
        return travailleurRepository.findAll();
    }

    @Override
    public Optional<Travailleur> getTravailleurById(String id) {
        return travailleurRepository.findById(id);
    }

    @Override
    public Travailleur updateTravailleur(String id, Travailleur userDetails) {
        return travailleurRepository.findById(id)
                .map(travailleur -> {
                    travailleur.setUsername(userDetails.getUsername());
                    travailleur.setEmail(userDetails.getEmail());
                    return travailleurRepository.save(travailleur);
                })
                .orElseThrow(() -> new RuntimeException("Travailleur not found with id: " + id));
    }

    @Override
    public void deleteTravailleur(String id) {
        travailleurRepository.deleteById(id);
    }

    // Fournisseur services
    @Override
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    @Override
    public Optional<Fournisseur> getFournisseurById(String id) {
        return fournisseurRepository.findById(id);
    }

    @Override
    public Fournisseur updateFournisseur(String id, Fournisseur userDetails) {
        return fournisseurRepository.findById(id)
                .map(fournisseur -> {
                    fournisseur.setUsername(userDetails.getUsername());
                    fournisseur.setEmail(userDetails.getEmail());
                    return fournisseurRepository.save(fournisseur);
                })
                .orElseThrow(() -> new RuntimeException("Fournisseur not found with id: " + id));
    }

    @Override
    public void deleteFournisseur(String id) {
        fournisseurRepository.deleteById(id);
    }
}
