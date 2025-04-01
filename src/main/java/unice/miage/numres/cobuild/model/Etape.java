package unice.miage.numres.cobuild.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unice.miage.numres.cobuild.util.StatutEtape;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "etapes")
public class Etape extends AbstractBaseEntity {

    private String nom;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatutEtape statut = StatutEtape.COMMENCEE;

    @ManyToOne
    @JoinColumn(name = "tache_id", nullable = false)
    @JsonBackReference(value = "tache-etapes")
    private Tache tache;
}

