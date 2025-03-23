package unice.miage.numres.cobuild.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import unice.miage.numres.cobuild.model.Message;
import unice.miage.numres.cobuild.services.MessageService;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    
    @Autowired
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestParam String to,
            @RequestParam String content,
            @AuthenticationPrincipal UserDetails userDetails) {
        messageService.sendMessage(userDetails.getUsername(), to, content);
        return ResponseEntity.ok("Message sent.");
    }

    @GetMapping("/conversation/{username}")
    public ResponseEntity<List<Message>> getConversation(
            @PathVariable String username,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                messageService.getConversation(userDetails.getUsername(), username));
    }

    @GetMapping("/inbox")
    public ResponseEntity<List<Message>> getInbox(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                messageService.getInbox(userDetails.getUsername()));
    }
}
