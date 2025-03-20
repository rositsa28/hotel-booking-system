package bg.softuni.hotelbookingsystem.scheduler;

import bg.softuni.hotelbookingsystem.payment.Payment;
import bg.softuni.hotelbookingsystem.payment.PaymentClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class SyncPaymentStatuses {

    private final PaymentClientService paymentClientService;

    public SyncPaymentStatuses(PaymentClientService paymentClientService) {

        this.paymentClientService = paymentClientService;
    }

    @Async
    @Scheduled(cron = "0 */15 * * * *")
    public void syncPaymentStatuses() {
        List<Payment> payments = paymentClientService.findAllPayments();
        for (Payment payment : payments) {
            paymentClientService.updatePaymentStatus(payment.getPaymentId());
        }
    }
}



