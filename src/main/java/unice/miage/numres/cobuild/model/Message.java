package unice.miage.numres.cobuild.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message extends AbstractBaseEntity{
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Utilisateur sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Utilisateur receiver;

    @Column(nullable = false)
    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();
}
