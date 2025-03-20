package bg.softuni.hotelbookingsystem.payment;

import bg.softuni.hotelbookingsystem.booking.service.BookingService;
import bg.softuni.hotelbookingsystem.web.dto.PaymentRequest;
import bg.softuni.hotelbookingsystem.web.dto.PaymentResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentClientService {
    private final PaymentFeignClient paymentFeignClient;
    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;

    @Autowired
    public PaymentClientService(PaymentFeignClient paymentFeignClient, PaymentRepository paymentRepository, @Lazy BookingService bookingService) {
        this.paymentFeignClient = paymentFeignClient;
        this.paymentRepository = paymentRepository;
        this.bookingService = bookingService;
    }

    @Transactional
    public PaymentResponse createPayment(UUID bookingId, BigDecimal amount) {
        PaymentRequest request = new PaymentRequest(bookingId, amount);
        PaymentResponse response = paymentFeignClient.createPayment(request);

        if (response == null || response.getPaymentId() == null) {
            throw new RuntimeException("Payment creation failed or invalid response from Payment Microservice.");
        }

        Payment payment = Payment.builder()
                .paymentId(response.getPaymentId())
                .paymentStatus(response.getPaymentStatus())
                .booking(bookingService.findById(bookingId))
                .build();

        paymentRepository.save(payment);
        return response;
    }

    public PaymentResponse getPaymentStatusResponse(UUID paymentId) {
        PaymentResponse response = paymentFeignClient.getPaymentStatus(paymentId);
        if (response == null) {
            return PaymentResponse.builder()
                    .paymentId(paymentId)
                    .paymentStatus(PaymentStatus.FAILED)
                    .build();
        }
        return response;
    }

    @Transactional
    public PaymentResponse updatePaymentStatus(UUID paymentId) {
        PaymentResponse statusResponse = getPaymentStatusResponse(paymentId);

        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment reference not found"));

        payment.setPaymentStatus(statusResponse.getPaymentStatus());
        paymentRepository.save(payment);

        return statusResponse;
    }

    public List<Payment> findAllByUserId(UUID userId) {
        return paymentRepository.findAllByBooking_User_Id(userId);
    }


    public List<Payment> findAllPayments() {
       return paymentRepository.findAll();
    }
}


