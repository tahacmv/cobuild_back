package unice.miage.numres.cobuild.servicesImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Message;
import unice.miage.numres.cobuild.model.Utilisateur;
import unice.miage.numres.cobuild.repository.MessageRepository;
import unice.miage.numres.cobuild.repository.UserRepository;
import unice.miage.numres.cobuild.services.MessageService;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public void sendMessage(String senderUsername, String receiverUsername, String content) {
        Utilisateur sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Utilisateur receiver = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);
    }

    @Override
    public List<Message> getConversation(String user1, String user2) {
        Utilisateur u1 = userRepository.findByUsername(user1)
                .orElseThrow(() -> new RuntimeException("User 1 not found"));
        Utilisateur u2 = userRepository.findByUsername(user2)
                .orElseThrow(() -> new RuntimeException("User 2 not found"));

        List<Message> convo1 = messageRepository.findBySenderAndReceiverOrderByTimestampAsc(u1, u2);
        List<Message> convo2 = messageRepository.findBySenderAndReceiverOrderByTimestampAsc(u2, u1);

        List<Message> conversation = new ArrayList<>();
        conversation.addAll(convo1);
        conversation.addAll(convo2);
        conversation.sort(Comparator.comparing(Message::getTimestamp));
        return conversation;
    }

    @Override
    public List<Message> getInbox(String username) {
        return messageRepository.findByReceiverUsernameOrSenderUsernameOrderByTimestampDesc(username, username);
    }
}
