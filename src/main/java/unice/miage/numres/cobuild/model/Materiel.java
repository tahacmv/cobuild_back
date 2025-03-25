package unice.miage.numres.cobuild.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "materiel")
public class Materiel extends AbstractBaseEntity {
    String nom;
    String description;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id", nullable = false)
    @JsonBackReference(value="materiel-fournisseur")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    @JsonBackReference(value="materiel-projet")
    private Projet projet;
}
