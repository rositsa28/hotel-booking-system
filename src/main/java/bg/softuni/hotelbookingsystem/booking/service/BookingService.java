package bg.softuni.hotelbookingsystem.booking.service;

import bg.softuni.hotelbookingsystem.booking.model.Booking;
import bg.softuni.hotelbookingsystem.booking.repository.BookingRepository;
import bg.softuni.hotelbookingsystem.payment.PaymentClientService;
import bg.softuni.hotelbookingsystem.room.model.Room;
import bg.softuni.hotelbookingsystem.room.service.RoomService;
import bg.softuni.hotelbookingsystem.user.model.User;
import bg.softuni.hotelbookingsystem.user.service.UserService;
import bg.softuni.hotelbookingsystem.web.dto.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;


@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EmailService emailService;
    private final UserService userService;
    private final RoomService roomService;
    private final PaymentClientService paymentClientService;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          EmailService emailService,
                          UserService userService,
                          RoomService roomService,
                          PaymentClientService paymentClientService) {
        this.bookingRepository = bookingRepository;
        this.emailService = emailService;
        this.userService = userService;
        this.roomService = roomService;
        this.paymentClientService = paymentClientService;
    }

    public List<Booking> getUserBookings(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public Booking createBooking(BookingRequest bookingRequest) {
        Room room = roomService.getById(bookingRequest.getRoomId());
        if (!room.isAvailable()) {
            throw new RuntimeException("Room is already booked/unavailable.");
        }

        Booking booking = new Booking();
        booking.setCheckIn(bookingRequest.getCheckIn());
        booking.setCheckOut(bookingRequest.getCheckOut());
        booking.setRoom(room);
        User user = userService.findById(bookingRequest.getUserId());
        booking.setUser(user);
        booking.setPaid(false);

        room.setAvailable(false);
        roomService.save(room);

        Booking savedBooking = bookingRepository.save(booking);

        UUID paymentId = paymentClientService.createPayment(savedBooking.getId(), room.getPrice());
        if (paymentId == null) {
            throw new RuntimeException("Payment failed. Booking cannot be completed.");
        }

        emailService.sendBookingConfirmation(user.getEmail(), "Booking confirmed for: " + savedBooking.getCheckIn());

        return savedBooking;
    }
    @Transactional
    public void cancelBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Room room = booking.getRoom();
        room.setAvailable(true);
        roomService.save(room);

        bookingRepository.deleteById(bookingId);
    }

    public List<Booking> findByUser(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }

    public UUID findByUsername(String username) {
        return userService.findByUsername(username);
    }

    public Booking findById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Scheduled(cron = "0 0/15 * * * *")
    public void cancelUnpaidBookings() {
        List<Booking> unpaidBookings = bookingRepository.findByPaidFalse();
        for (Booking booking : unpaidBookings) {
            booking.setPaid(false);
            bookingRepository.save(booking);
            emailService.sendBookingCancellation(
                    booking.getUser().getEmail(),
                    "Your booking on " + booking.getCheckIn() + " was canceled due to non-payment."
            );
        }
    }
}

