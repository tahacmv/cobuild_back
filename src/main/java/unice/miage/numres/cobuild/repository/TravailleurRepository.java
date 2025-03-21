package unice.miage.numres.cobuild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;
import unice.miage.numres.cobuild.model.Travailleur;

@Repository
public interface TravailleurRepository extends JpaRepository<Travailleur, String> {
    Optional<Travailleur> findByUsername(String username);

}