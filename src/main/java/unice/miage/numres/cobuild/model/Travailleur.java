package unice.miage.numres.cobuild.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "travailleurs")
public class Travailleur extends Utilisateur {

    @ElementCollection
    private List<String> competences;

    @ManyToMany(mappedBy = "travailleurs")
    @JsonIgnore
    private List<Tache> taches;

    @OneToOne(mappedBy = "travailleur")
    @JsonBackReference(value = "travailleur-poste")
    private Poste poste;
}
