package unice.miage.numres.cobuild.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unice.miage.numres.cobuild.util.StatutCandidature;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidatures")
public class Candidature extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "travailleur_id", nullable = false)
    private Travailleur travailleur;

    @ManyToOne
    @JoinColumn(name = "poste_id", nullable = false)
    @JsonBackReference(value = "poste-candidatures")
    private Poste poste;

    @Enumerated(EnumType.STRING)
    private StatutCandidature statut = StatutCandidature.EN_ATTENTE;

    private LocalDateTime dateCandidature = LocalDateTime.now();
}