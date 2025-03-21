package unice.miage.numres.cobuild.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "porteur")
public class PorteurDeProjet extends Utilisateur {

    @OneToMany(mappedBy = "porteurDeProjet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Projet> projets;
}
