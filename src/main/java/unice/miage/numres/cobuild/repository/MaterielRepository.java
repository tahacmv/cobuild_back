package unice.miage.numres.cobuild.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import unice.miage.numres.cobuild.model.Fournisseur;
import unice.miage.numres.cobuild.model.Materiel;

public interface MaterielRepository extends JpaRepository<Materiel, String> {
        List<Materiel> findByFournisseur(Fournisseur fournisseur);
}
