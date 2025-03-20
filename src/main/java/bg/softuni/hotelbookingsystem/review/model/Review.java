package bg.softuni.hotelbookingsystem.review.model;

import bg.softuni.hotelbookingsystem.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User user;

    @Column(length = 1000, nullable = false)
    private String comment;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
