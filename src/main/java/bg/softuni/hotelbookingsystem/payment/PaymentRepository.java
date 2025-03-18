package bg.softuni.hotelbookingsystem.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByPaymentId(UUID paymentId);

    List<Payment> findAllByBooking_User_Id(UUID userId);

    List<Payment> findAllByPaymentStatus(PaymentStatus status);
}
