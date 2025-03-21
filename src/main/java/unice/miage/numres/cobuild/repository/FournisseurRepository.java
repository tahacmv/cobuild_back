package unice.miage.numres.cobuild.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.Fournisseur;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {
    Optional<Fournisseur> findByUsername(String username);
}