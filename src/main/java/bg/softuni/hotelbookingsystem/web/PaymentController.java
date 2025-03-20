package bg.softuni.hotelbookingsystem.web;

import bg.softuni.hotelbookingsystem.payment.PaymentClientService;
import bg.softuni.hotelbookingsystem.web.dto.PaymentRequest;
import bg.softuni.hotelbookingsystem.web.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentClientService paymentClientService;

    @Autowired
    public PaymentController(PaymentClientService paymentClientService) {
        this.paymentClientService = paymentClientService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentClientService.createPayment(request.getBookingId(), request.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable("id") UUID paymentId) {
        PaymentResponse response = paymentClientService.getPaymentStatusResponse(paymentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/sync")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(@PathVariable("id") UUID paymentId) {
        PaymentResponse response = paymentClientService.updatePaymentStatus(paymentId);
        return ResponseEntity.ok(response);
    }
}