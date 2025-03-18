package bg.softuni.hotelbookingsystem.payment;

import bg.softuni.hotelbookingsystem.web.dto.PaymentRequest;
import bg.softuni.hotelbookingsystem.web.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "payment-svc", url = "http://localhost:8081")
public interface PaymentFeignClient {

    @PostMapping("/payments")
    PaymentResponse createPayment(@RequestBody PaymentRequest request);

    @GetMapping("/payments/{id}")
    PaymentResponse getPaymentStatus(@PathVariable("id") UUID paymentId);
}
