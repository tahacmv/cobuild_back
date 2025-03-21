package unice.miage.numres.cobuild.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "administrateurs")
public class Administrateur extends Utilisateur {

}
