package unice.miage.numres.cobuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.Etape;

@Repository
public interface EtapeRepository extends JpaRepository<Etape, String>{

    List<Etape> findByTacheId(String taskId);

}
