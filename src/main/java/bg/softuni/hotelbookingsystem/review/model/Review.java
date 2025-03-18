package bg.softuni.hotelbookingsystem.review.model;


import bg.softuni.hotelbookingsystem.hotel.model.Hotel;
import bg.softuni.hotelbookingsystem.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @ManyToOne
    private Hotel hotel;

    @Column(length = 1000, nullable = false)
    private String comment;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private LocalDate createdAt;

}
