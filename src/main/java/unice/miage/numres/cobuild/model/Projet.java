package unice.miage.numres.cobuild.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "projet")
public class Projet extends AbstractBaseEntity {
    private String nom;
    private String description;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "porteur_id", nullable = false)
    @JsonBackReference
    private PorteurDeProjet porteurDeProjet;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tache> taches;

    @ManyToMany
    @JoinTable(
        name = "projet_volunteers",
        joinColumns = @JoinColumn(name = "projet_id"),
        inverseJoinColumns = @JoinColumn(name = "travailleur_id")
    )
    private List<Travailleur> volontaires;
}
