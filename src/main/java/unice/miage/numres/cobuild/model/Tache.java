package unice.miage.numres.cobuild.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tache")
public class Tache extends AbstractBaseEntity {
    private String nom;
    private String description;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    @JsonBackReference
    private Projet projet;

    @ManyToOne
    @JoinColumn(name = "travailleur_id")
    private Travailleur travailleur;
}
