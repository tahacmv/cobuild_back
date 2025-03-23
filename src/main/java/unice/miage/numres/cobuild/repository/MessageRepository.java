package unice.miage.numres.cobuild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unice.miage.numres.cobuild.model.Message;
import unice.miage.numres.cobuild.model.Utilisateur;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findBySenderAndReceiverOrderByTimestampAsc(Utilisateur sender, Utilisateur receiver);

    List<Message> findByReceiverUsernameOrSenderUsernameOrderByTimestampDesc(String receiver, String sender);
}
