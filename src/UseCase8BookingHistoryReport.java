/**
 * Book My Stay App
 * Demonstrates booking history tracking and reporting.
 *
 * @author Isha
 * @version 8.0
 */

import java.util.*;

// ---------------- Reservation ----------------
class Reservation {

    String reservationID;
    String guestName;
    String roomType;

    public Reservation(String reservationID, String guestName, String roomType) {
        this.reservationID = reservationID;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationID +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}

// ---------------- Booking History ----------------
class BookingHistory {

    // List to store confirmed reservations
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add reservation to history
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation stored in history: " + reservation.reservationID);
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return history;
    }
}

// ---------------- Booking Report Service ----------------
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all booking history
    public void displayBookingHistory() {

        System.out.println("\nBooking History:");
        System.out.println("------------------------------");

        for (Reservation r : history.getReservations()) {
            r.displayReservation();
        }
    }

    // Generate simple summary report
    public void generateSummaryReport() {

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : history.getReservations()) {
            roomTypeCount.put(r.roomType,
                    roomTypeCount.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("\nBooking Summary Report:");
        System.out.println("------------------------------");

        for (String roomType : roomTypeCount.keySet()) {
            System.out.println(roomType + " bookings: " + roomTypeCount.get(roomType));
        }
    }
}

// ---------------- Main Application ----------------
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 8.0");
        System.out.println("Booking History & Reporting");
        System.out.println("=================================");

        BookingHistory history = new BookingHistory();

        // Example confirmed reservations
        history.addReservation(new Reservation("RES101", "Alice", "Single Room"));
        history.addReservation(new Reservation("RES102", "Bob", "Double Room"));
        history.addReservation(new Reservation("RES103", "Charlie", "Single Room"));
        history.addReservation(new Reservation("RES104", "David", "Suite Room"));

        BookingReportService reportService = new BookingReportService(history);

        // Display booking history
        reportService.displayBookingHistory();

        // Generate summary report
        reportService.generateSummaryReport();
    }
}