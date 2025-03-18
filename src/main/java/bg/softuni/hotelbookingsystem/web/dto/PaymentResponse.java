package bg.softuni.hotelbookingsystem.web.dto;

import bg.softuni.hotelbookingsystem.payment.PaymentStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private UUID paymentId;
    private PaymentStatus paymentStatus;
}
