package unice.miage.numres.cobuild.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.PorteurDeProjet;

@Repository
public interface PorteurDeProjetRepository extends JpaRepository<PorteurDeProjet, String> {
    Optional<PorteurDeProjet> findByUsername(String username);
}