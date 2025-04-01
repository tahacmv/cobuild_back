package unice.miage.numres.cobuild.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    private boolean archived = false;
    @Column(name = "image_url")
    private String imageUrl;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    private String adresse;


    @ManyToOne
    @JoinColumn(name = "porteur_id", nullable = false)
    @JsonBackReference
    private PorteurDeProjet porteurDeProjet;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value="projet-taches")
    private List<Tache> taches;
    
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    @JsonManagedReference(value="projet-poste")
    private List<Poste> postes;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value="materiel-projet")
    private List<Materiel> materiels;
}
