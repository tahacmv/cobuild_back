package unice.miage.numres.cobuild.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_posts")
public class Poste extends AbstractBaseEntity {

    private String titre;
    private String description;
    private Double salaire;

    @ElementCollection
    private List<String> competencesRequises;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    @JsonBackReference(value = "projet-poste")
    private Projet projet;

    
    @OneToOne
    @JoinColumn(name = "travailleur_id", unique=false)
    @JsonManagedReference(value = "travailleur-poste")
    private Travailleur travailleur;

    @OneToMany(mappedBy = "poste", cascade = CascadeType.ALL)
    private List<Candidature> candidatures;
}
