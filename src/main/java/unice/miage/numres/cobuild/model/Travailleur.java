package unice.miage.numres.cobuild.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "travailleur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidature> candidatures;

    @ManyToMany(mappedBy = "volontaires")
    @JsonIgnore
    private List<Projet> projetsVolontaires;
}
