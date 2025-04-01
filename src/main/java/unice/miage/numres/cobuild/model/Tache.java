package unice.miage.numres.cobuild.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JsonBackReference(value="projet-taches")
    private Projet projet;

    @ManyToMany
    @JoinTable(
        name = "tache_travailleurs",
        joinColumns = @JoinColumn(name = "tache_id"),
        inverseJoinColumns = @JoinColumn(name = "travailleur_id")
    )
    private List<Travailleur> travailleurs;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etape> etapes;
}
