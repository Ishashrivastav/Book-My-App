/**
 * Book My Stay App
 * Demonstrates booking request handling using FIFO Queue.
 *
 * @author Isha
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

// ---------------- Reservation ----------------
class Reservation {

    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// ---------------- Booking Request Queue ----------------
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.guestName);
    }

    // Display queued requests
    public void displayRequests() {
        System.out.println("\nCurrent Booking Requests (FIFO Order):");
        System.out.println("--------------------------------------");

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
    }
}

// ---------------- Main Application ----------------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 5.0");
        System.out.println("Booking Request Queue (FIFO)");
        System.out.println("=================================");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submitting booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue order
        bookingQueue.displayRequests();

        System.out.println("\nRequests are stored in arrival order.");
        System.out.println("Room allocation will happen in the next use case.");
    }
}