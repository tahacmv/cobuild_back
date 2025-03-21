package unice.miage.numres.cobuild.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Fournisseur;
import unice.miage.numres.cobuild.model.PorteurDeProjet;
import unice.miage.numres.cobuild.model.Travailleur;
import unice.miage.numres.cobuild.model.Utilisateur;
import unice.miage.numres.cobuild.services.AdminService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<Utilisateur>> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Utilisateur> updateUser(@PathVariable String id, @RequestBody Utilisateur userDetails) {
        return ResponseEntity.ok(adminService.updateUser(id, userDetails));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{userId}/assign-role")
    public ResponseEntity<String> assignRole(@PathVariable String userId, @RequestParam String roleName) {
        adminService.assignRole(userId, roleName);
        return ResponseEntity.ok("Role assigned successfully.");
    }

    // Porteur de projet endpoints
    @GetMapping("/porteurs")
    public List<PorteurDeProjet> getAllPorteurs() {
        return adminService.getAllPorteurs();
    }

    @GetMapping("/porteurs/{id}")
    public ResponseEntity<PorteurDeProjet> getPorteurById(@PathVariable String id) {
        Optional<PorteurDeProjet> porteur = adminService.getPorteurById(id);
        return porteur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/porteurs/{id}")
    public ResponseEntity<PorteurDeProjet> updatePorteur(@PathVariable String id, @RequestBody PorteurDeProjet userDetails) {
        try {
            return ResponseEntity.ok(adminService.updatePorteur(id, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/porteurs/{id}")
    public ResponseEntity<Void> deletePorteur(@PathVariable String id) {
        adminService.deletePorteur(id);
        return ResponseEntity.noContent().build();
    }

    // Travailleur endpoints
    @GetMapping("/travailleurs")
    public List<Travailleur> getAllTravailleurs() {
        return adminService.getAllTravailleurs();
    }

    @GetMapping("/travailleurs/{id}")
    public ResponseEntity<Travailleur> getTravailleurById(@PathVariable String id) {
        Optional<Travailleur> travailleur = adminService.getTravailleurById(id);
        return travailleur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/travailleurs/{id}")
    public ResponseEntity<Travailleur> updateTravailleur(@PathVariable String id, @RequestBody Travailleur userDetails) {
        try {
            return ResponseEntity.ok(adminService.updateTravailleur(id, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/travailleurs/{id}")
    public ResponseEntity<Void> deleteTravailleur(@PathVariable String id) {
        adminService.deleteTravailleur(id);
        return ResponseEntity.noContent().build();
    }

    // Fournisseur endpoints
    @GetMapping("/fournisseurs")
    public List<Fournisseur> getAllFournisseurs() {
        return adminService.getAllFournisseurs();
    }

    @GetMapping("/fournisseurs/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable String id) {
        Optional<Fournisseur> fournisseur = adminService.getFournisseurById(id);
        return fournisseur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/fournisseurs/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable String id, @RequestBody Fournisseur userDetails) {
        try {
            return ResponseEntity.ok(adminService.updateFournisseur(id, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/fournisseurs/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable String id) {
        adminService.deleteFournisseur(id);
        return ResponseEntity.noContent().build();
    }

}
