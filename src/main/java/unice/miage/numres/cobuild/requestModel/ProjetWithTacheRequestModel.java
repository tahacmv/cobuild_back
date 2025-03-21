package unice.miage.numres.cobuild.requestModel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import unice.miage.numres.cobuild.model.Projet;
import unice.miage.numres.cobuild.model.Tache;

@Getter
@Setter
public class ProjetWithTacheRequestModel {
    private Projet projet;
    private List<Tache> taches;
}
