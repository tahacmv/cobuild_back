package unice.miage.numres.cobuild.services;

import java.util.List;

import unice.miage.numres.cobuild.model.Message;


public interface MessageService {
    void sendMessage(String senderUsername, String receiverUsername, String content);
    List<Message> getConversation(String user1, String user2);
    List<Message> getInbox(String username);
}