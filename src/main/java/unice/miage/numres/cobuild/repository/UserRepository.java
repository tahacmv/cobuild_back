package unice.miage.numres.cobuild.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.Utilisateur;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, String> {
    Optional<Utilisateur> findByUsername(String username);
}