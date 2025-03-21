package unice.miage.numres.cobuild.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;
import lombok.Getter;

@Entity
@Getter
@Setter
public class Candidature extends AbstractBaseEntity {
    String status;

    @ManyToOne
    @JoinColumn(name = "travailleur_id", nullable = false)
    private Travailleur travailleur;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
}
