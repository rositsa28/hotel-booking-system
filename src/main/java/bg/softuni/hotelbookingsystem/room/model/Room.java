package bg.softuni.hotelbookingsystem.room.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean available;

    @Column(length = 1000)
    private String description;

    private String imageUrl;

}
